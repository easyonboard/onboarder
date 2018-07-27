package service;

import com.google.common.hash.Hashing;
import dao.*;
import dto.CheckListDTO;
import dto.LeaveCheckListDTO;
import dto.UserDTO;
import dto.UserInformationDTO;
import dto.mapper.*;
import entity.CheckList;
import entity.LeaveCheckList;
import entity.User;
import entity.UserInformation;
import exception.types.EntityNotFoundException;
import exception.types.InvalidDataException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import validator.UserValidator;

import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static exception.Constants.NOT_FOUND_EXCEPTION;
import static exception.Constants.userNotFound;

/**
 * Service for {@link UserDTO}
 */
@Service
public class UserService {

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private UserValidator userValidator;

    @Autowired
    private UserInformationDAO userInformationDAO;

    @Autowired
    private CheckListRepository checkListRepository;

    @Autowired
    private LeaveCheckListRepository leaveCheckListRepository;

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

        Optional<User> entity = userDAO.findUserByUsername(username);
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
        User appUser = userDAO.persistEntity(userMapper.mapToEntity(userDTO, user));

        Optional<User> optional = userDAO.findUserByUsername(userInformationDTO.getBuddyUser().getUsername());
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

    public void updateUser(UserDTO userUpdated) throws InvalidDataException, EntityNotFoundException {

        Optional<User> user = userDAO.findUserByUsername(userUpdated.getUsername());

        if (user.isPresent()) {
            if (userUpdated.getPassword() != null) {
                userUpdated.setPassword(encrypt(userUpdated.getPassword()));
            }
            User entity = userMapper.mapToEntity(userUpdated, user.get());
            userValidator.validateUserData(userMapper.mapToDTO(entity));
            userDAO.persistEntity(entity);
        } else {
            throw new EntityNotFoundException(NOT_FOUND_EXCEPTION);
        }
    }

    public List<UserDTO> getAllUsers() {

        List<User> allUsersFromDb = userDAO.getAllUsers();
        return userMapper.entitiesToDTOs(allUsersFromDb);
    }

    public List<UserInformationDTO> getAllNewUsers() {

        return userInformationMapper.entitiesToDTOs(userInformationDAO.getAllNewUsers());
    }

    public List<UserDTO> searchByName(String name) {

        return userMapper.entitiesToDTOs(userDAO.searchByName(name));
    }

    public List<UserDTO> getUsersInDepartmentForUser(String username) {

        String department = getDepartmentForUser(username);
        return userMapper.entitiesToDTOs(userDAO.getUsersInDepartment(department));
    }

    public String getDepartmentForUser(String username) {

        return userDAO.getDepartmentForUser(username);
    }

    public Map getCheckList(UserDTO userDTO) {

        return userDAO.getCheckListMapForUser(userDAO.findEntity(userDTO.getIdUser()));
    }

    public void saveCheckListForUser(String user, CheckListDTO checkList) {

        User userEntity = userDAO.findUserByUsername(user).get();
        checkList.setUserAccount(userMapper.mapToDTO(userEntity));
        CheckList checkListEntity = checkListRepository.findByUserAccount(userEntity);
        checkListMapper.mapToEntity(checkList, checkListEntity);
        checkListRepository.save(checkListEntity);
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

        Optional<User> userOptional = userDAO.findUserByUsername(username);
        if (userOptional.isPresent()) {
            User userEntity = userOptional.get();

            if (canUserBeDeleted(userEntity)) {

                UserInformation userInformationEntity = userInformationDAO.findUserInformationByUser(userEntity);
                if (userInformationEntity != null) {
                    userInformationEntity.setBuddyUser(null);
                    userInformationEntity.setLocation(null);

                    userInformationDAO.deleteEntity(userInformationEntity);
                }

                CheckList checkListEntity = checkListRepository.findByUserAccount(userEntity);
                if (checkListEntity != null) {
                    checkListRepository.delete(checkListEntity);
                }

                LeaveCheckList leavecheckListEntity = leaveCheckListRepository.findLeaveCheckListByUserAccount(userEntity);
                if (leavecheckListEntity != null) {
                    leaveCheckListRepository.delete(leavecheckListEntity);
                }
                tutorialDAO.removeUserFromTutorialContactList(userEntity);
                eventRepository.removeUserFromEnrolledList(userEntity);
                eventRepository.removeContactPersonFromEvents(userEntity);
                userInformationDAO.setBuddyToNull(userEntity);
                userDAO.deleteEntity(userEntity);
                return true;
            } else {
                return false;
            }

        } else
            throw new EntityNotFoundException(userNotFound(username));

    }

    public UserInformationDTO getUserInformationForUser(String username) {

        return userInformationMapper.mapToDTO(
                userInformationDAO.findUserInformationByUser(userDAO.findUserByUsername(username).get()));
    }

    public List<String> getAllUsersNameAndEmail() {

        List<User> users = userDAO.getAllUsers();
        List<String> usersList = new ArrayList<>();

        users.forEach(user -> usersList.add(user.getName() + '(' + user.getMsgMail() + ')'));

        return usersList;
    }

    public void updateUserPassword(String username, String password) throws EntityNotFoundException {

        Optional<User> userOptional = userDAO.findUserByUsername(username);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            if (password != null) {
                user.setPassword(encrypt(password));
            }
            userDAO.persistEntity(user);
        } else {
            throw new EntityNotFoundException(NOT_FOUND_EXCEPTION);
        }

    }

    public boolean getStatusMailForUser(String username) throws EntityNotFoundException {

        Optional<User> userOptional = userDAO.findUserByUsername(username);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            return checkListRepository.getValueForMailSent(user);
        } else {
            throw new EntityNotFoundException(NOT_FOUND_EXCEPTION);
        }
    }

    public LeaveCheckListDTO getLeaveCheckListForUser(String username) throws EntityNotFoundException {

        Optional<User> user = userDAO.findUserByUsername(username);
        if (user.isPresent()) {
            User userEntity = user.get();
            LeaveCheckList leaveCheckList = leaveCheckListRepository.findLeaveCheckListByUserAccount(userEntity);
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
                        return leaveCheckListMapper.mapToDTO(leaveCheckListRepository.save(leaveCheckList));

                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
                leaveCheckListRepository.save(leaveCheckList);
            }
            return leaveCheckListMapper.mapToDTO(leaveCheckList);
        } else
            throw new EntityNotFoundException(userNotFound(username));

    }

    public LeaveCheckListDTO saveLeaveCheckList(LeaveCheckListDTO leaveCheckList) {

        LeaveCheckList persistEntity = new LeaveCheckList();
        leaveCheckListMapper.mapToEntity(leaveCheckList, persistEntity);
        leaveCheckListRepository.save(persistEntity);
        return leaveCheckListMapper.mapToDTO(leaveCheckListRepository.findOne(leaveCheckList.getIdCheckList()));
    }

    private boolean canUserBeDeleted(User user) {

        LeaveCheckList leaveCheckList = leaveCheckListRepository.findLeaveCheckListByUserAccount(user);
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

        return !(userDAO.findUserByUsername(username).isPresent() || userDAO.findUserByMsgMail(msgMail).isPresent());
    }
}
