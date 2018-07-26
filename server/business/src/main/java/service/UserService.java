package service;

import com.google.common.hash.Hashing;
import dao.*;
import dto.*;
import dto.mapper.*;
import entity.CheckList;
import entity.LeaveCheckList;
import entity.User;
import entity.UserInformation;
import exception.types.InvalidDataException;
import exception.types.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import validator.UserValidator;

import javax.persistence.NoResultException;
import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;
import java.util.*;

import static exception.Constants.userNotFound;

/**
 * Service for {@link UserDTO}
 */
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserValidator userValidator;

    @Autowired
    private UserInformationDAO userInformationDAO;

    @Autowired
    private CheckListDAO checkListDAO;

    @Autowired
    private LeaveCheckListDAO leaveCheckListDAO;

    @Autowired
    private UserInformationService userInformationService;

    @Autowired
    private CheckListService checkListService;

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private TutorialDAO tutorialDAO;

    private UserMapper userMapper = UserMapper.INSTANCE;

    private CheckListMapper checkListMapper = CheckListMapper.INSTANCE;

    private LeaveCheckListMapper leaveCheckListMapper = LeaveCheckListMapper.INSTANCE;

    private UserInformationMapper userInformationMapper = UserInformationMapper.INSTANCE;

    private LocationMapper locationMapper = LocationMapper.INSTANCE;

    public UserDTO findUserByUsername(String username) throws EntityNotFoundException {

        Optional<User> entity = userRepository.findByUsername(username);
        if (!entity.isPresent()) {
            throw new EntityNotFoundException(userNotFound(username));
        }
        return userMapper.mapToDTO(entity.get());
    }

    public void addUser(UserDTO userDTO, UserInformationDTO userInformationDTO) throws InvalidDataException {

        userDTO.setPassword(encrypt(userDTO.getUsername()));
        userValidator.validateUsername(userDTO.getUsername());
        userValidator.validateUserData(userDTO);

        User user = new User();
        User appUser = userRepository.save(userMapper.mapToEntity(userDTO, user));

        Optional<User> optional = userRepository.findByUsername(userInformationDTO.getBuddyUser().getUsername());
        if (optional.isPresent()) {
            User buddyUser = optional.get();
            userInformationService.addUserInfo(userInformationDTO, appUser, buddyUser);
        } else {
            userInformationService.addUserInfo(userInformationDTO, appUser, null);
        }

        checkListService.addCheckList(userInformationDTO, appUser);
    }

    public String encrypt(String initString) {

        return Hashing.sha256().hashString(initString, StandardCharsets.UTF_8).toString();
    }

    public void updateUser(UserDTO userUpdated) throws InvalidDataException {

        Optional<User> user = userRepository.findByUsername(userUpdated.getUsername());

        if (user.isPresent()) {
            if (userUpdated.getPassword() != null) {
                userUpdated.setPassword(encrypt(userUpdated.getPassword()));
            }
            User entity = userMapper.mapToEntity(userUpdated, user.get());
            userValidator.validateUserData(userMapper.mapToDTO(entity));
            userRepository.save(entity);
        }
    }

    public List<UserDTO> getAllUsers() {

        List<User> allUsersFromDb = userRepository.findAll();
        return userMapper.entitiesToDTOs(allUsersFromDb);
    }

    public List<UserInformationDTO> getAllNewUsers() {

        return userInformationMapper.entitiesToDTOs(userInformationDAO.getAllNewUsers());
    }

    public List<UserDTO> searchByName(String name) {

        return userMapper.entitiesToDTOs(userRepository.findAllByName(name));
    }

    public List<UserDTO> getUsersInDepartmentForUser(String username) {

        String department = getDepartmentForUser(username);
        return userMapper.entitiesToDTOs(userRepository.findAllByDepartment(department));
    }

    public String getDepartmentForUser(String username) {

        return userRepository.findDepartmentByUsername(username);
    }

    public Map getCheckList(UserDTO userDTO) {

        CheckList checkList = userRepository.getCheckListForUser(userRepository.findOne(userDTO.getIdUser()));
        try {
            Map checkListMap = new HashMap();
            Field fields[] = checkList.getClass().getDeclaredFields();
            boolean value = false;
            String attribute;
            for (int i = 2; i < fields.length; i++) {

                attribute = fields[i].getName();
                try {
                    fields[i].setAccessible(true);
                    value = (boolean) fields[i].get(checkList);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
                checkListMap.put(attribute, value);
            }

            return checkListMap;
        } catch (NoResultException e) {
            return null;
        }
    }

    public void saveCheckListForUser(String user, CheckListDTO checkList) {

        User userEntity = userRepository.findByUsername(user).get();
        checkList.setUserAccount(userMapper.mapToDTO(userEntity));
        CheckList checkListEntity = checkListDAO.findByUser(userEntity);
        checkListMapper.mapToEntity(checkList, checkListEntity);
        checkListDAO.persistEntity(checkListEntity);
    }

    /**
     * @param username
     * @return
     * @throws EntityNotFoundException delete user and all children from db
     *                                 searches all children and deletes them one by one
     *                                 searches all other users who have the user to be deleted as buddy and sets the
     *                                 buddy field TO NULL
     *                                 <p>
     *                                 <p>
     *                                 TO DO refactoring
     */
    public boolean deleteUser(String username) throws EntityNotFoundException {

        Optional<User> userOptional = userRepository.findByUsername(username);
        if (userOptional.isPresent()) {
            User userEntity = userOptional.get();

            if (canUserBeDeleted(userEntity)) {

                UserInformation userInformationEntity = userInformationDAO.findUserInformationByUser(userEntity);
                if (userInformationEntity != null) {
                    userInformationEntity.setBuddyUser(null);
                    userInformationEntity.setLocation(null);

                    userInformationDAO.deleteEntity(userInformationEntity);
                }

                CheckList checkListEntity = checkListDAO.findByUser(userEntity);
                if (checkListEntity != null) {
                    checkListDAO.deleteEntity(checkListEntity);
                }

                LeaveCheckList leavecheckListEntity = leaveCheckListDAO.findLeaveCheckListByUser(userEntity);
                if (leavecheckListEntity != null) {
                    leaveCheckListDAO.deleteEntity(leavecheckListEntity);
                }
                tutorialDAO.removeUserFromTutorialContactList(userEntity);
                eventRepository.removeUserFromEnrolledList(userEntity);
                eventRepository.removeContactPersonFromEvents(userEntity);
                userInformationDAO.setBuddyToNull(userEntity);
                userRepository.delete(userEntity);
                return true;
            } else {
                return false;
            }

        } else
            throw new EntityNotFoundException(userNotFound(username));

    }

    public UserInformationDTO getUserInformationForUser(String username) {

        return userInformationMapper.mapToDTO(
                userInformationDAO.findUserInformationByUser(userRepository.findByUsername(username).get()));
    }

    public List<String> getAllUsersNameAndEmail() {

        List<User> users = userRepository.findAll();
        List<String> usersList = new ArrayList<>();

        users.forEach(user -> usersList.add(user.getName() + '(' + user.getMsgMail() + ')'));

        return usersList;
    }

    public void updateUserPassword(String username, String password) {

        Optional<User> userOptional = userRepository.findByUsername(username);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            if (password != null) {
                user.setPassword(encrypt(password));
            }
            userRepository.save(user);
        }

    }

    public boolean getStatusMailForUser(String username) {

        Optional<User> userOptional = userRepository.findByUsername(username);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            return checkListDAO.getValueForMailSent(user);
        }
        return false;
    }

    public LeaveCheckListDTO getLeaveCheckListForUser(String username) throws EntityNotFoundException {

        Optional<User> user = userRepository.findByUsername(username);
        if (user.isPresent()) {
            User userEntity = user.get();
            LeaveCheckList leaveCheckList = leaveCheckListDAO.findLeaveCheckListByUser(userEntity);
            if (leaveCheckList == null) {
                leaveCheckList = new LeaveCheckList();
                leaveCheckList.setUserAccount(userEntity);
                Field[] fields = LeaveCheckList.class.getDeclaredFields();

                for (int i = 0; i < fields.length; i++) {    // all fields are set to false, except id and userAccount
                    fields[i].setAccessible(true);
                    try {
                        if (fields[i].getType() == Boolean.class) {
                            fields[i].set(leaveCheckList, false);
                        }
                        return leaveCheckListMapper.mapToDTO(leaveCheckListDAO.persistEntity(leaveCheckList));

                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
                leaveCheckListDAO.persistEntity(leaveCheckList);
            }
            return leaveCheckListMapper.mapToDTO(leaveCheckList);
        } else
            throw new EntityNotFoundException(userNotFound(username));

    }

    public LeaveCheckListDTO saveLeaveCheckList(LeaveCheckListDTO leaveCheckList) {

        LeaveCheckList persistEntity = new LeaveCheckList();
        leaveCheckListMapper.mapToEntity(leaveCheckList, persistEntity);
        leaveCheckListDAO.update(persistEntity);
        return leaveCheckListMapper.mapToDTO(leaveCheckListDAO.findEntity(leaveCheckList.getIdCheckList()));
    }

    private boolean canUserBeDeleted(User user) {

        LeaveCheckList leaveCheckList = leaveCheckListDAO.findLeaveCheckListByUser(user);
        if (leaveCheckList != null) {
            Field[] fields = LeaveCheckList.class.getDeclaredFields();
            for (int i = 0; i < fields.length; i++) {    // all fields are set to false, except id and userAccount
                fields[i].setAccessible(true);
                if (fields[i].getType() == boolean.class) {
                    try {
                        if (!fields[i].getBoolean(leaveCheckList)) {
                            return false;
                        }
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }

                }
            }
            return true;

        }
        return false;
    }

    public boolean checkUnicity(String username, String msgMail) {

        return !(userRepository.findByUsername(username).isPresent() || userRepository.findByMsgMail(msgMail).isPresent());
    }
}
