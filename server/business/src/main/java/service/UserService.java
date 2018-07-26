package service;

import com.google.common.hash.Hashing;
import dao.*;
import dto.*;
import dto.mapper.*;
import entity.CheckList;
import entity.LeaveCheckList;
import entity.User;
import entity.UserInformation;
import exception.types.DatabaseException;
import exception.types.FieldNotFoundException;
import exception.types.InvalidDataException;
import exception.types.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import validator.UserValidator;

import javax.persistence.NoResultException;
import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;
import java.util.*;

import static exception.Constants.*;

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
    private CheckListRepository checkListRepository;

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

    public void addUser(UserDTO userDTO, UserInformationDTO userInformationDTO) throws InvalidDataException, DatabaseException {

        userDTO.setPassword(encrypt(userDTO.getUsername()));
        userValidator.validateUsername(userDTO.getUsername());
        userValidator.validateUserData(userDTO);

        User user = new User();
        User appUser = userRepository.save(userMapper.mapToEntity(userDTO, user));

        if (appUser == null) {
            throw new DatabaseException(USER_SAVE_DATABASE_EXCEPTION);
        }

        Optional<User> optionalUser = userRepository.findByUsername(userInformationDTO.getBuddyUser().getUsername());
        if (optionalUser.isPresent()) {
            User buddyUser = optionalUser.get();
            userInformationService.addUserInfo(userInformationDTO, appUser, buddyUser);
        } else {
            userInformationService.addUserInfo(userInformationDTO, appUser, null);
        }

        checkListService.addCheckList(userInformationDTO, appUser);
    }

    public String encrypt(String initString) {

        return Hashing.sha256().hashString(initString, StandardCharsets.UTF_8).toString();
    }

    public void updateUser(UserDTO userUpdated) throws InvalidDataException, EntityNotFoundException, DatabaseException {

        Optional<User> user = userRepository.findByUsername(userUpdated.getUsername());

        if (user.isPresent()) {
            if (userUpdated.getPassword() != null) {
                userUpdated.setPassword(encrypt(userUpdated.getPassword()));
            }
            User entity = userMapper.mapToEntity(userUpdated, user.get());
            userValidator.validateUserData(userMapper.mapToDTO(entity));
            if (userRepository.save(entity) == null) {
                throw new DatabaseException(USER_SAVE_DATABASE_EXCEPTION);
            }
        } else {
            throw new EntityNotFoundException(userNotFound(userUpdated.getUsername()));
        }
    }

    public List<UserDTO> getAllUsers() throws EntityNotFoundException {

        List<User> allUsersFromDb = userRepository.findAll();
        if (allUsersFromDb.isEmpty()) {
            throw new EntityNotFoundException(USERS_NOT_FOUND_EXCEPTION);
        }

        return userMapper.entitiesToDTOs(allUsersFromDb);
    }

    public List<UserInformationDTO> getAllNewUsers() throws EntityNotFoundException {

        List<UserInformation> newUsers = userInformationDAO.getAllNewUsers();
        if (newUsers.isEmpty()) {
            throw new EntityNotFoundException(NEW_USERS_NOT_FOUND_EXCEPTION);
        }

        return userInformationMapper.entitiesToDTOs(newUsers);
    }

    public List<UserDTO> searchByName(String name) throws EntityNotFoundException {

        List<User> users = userRepository.findAllByName(name);
        if (users.isEmpty()) {
            throw new EntityNotFoundException(USERS_MATCHING_STRING_NOT_FOUND_EXCEPTION);
        }

        return userMapper.entitiesToDTOs(users);
    }

    public List<UserDTO> getUsersInDepartmentForUser(String username) throws EntityNotFoundException, FieldNotFoundException {

        String department = getDepartmentForUser(username);
        List<User> users = userRepository.findAllByDepartment(department);
        if (users.isEmpty()) {
            throw new EntityNotFoundException(userForDepartmentNotFound(department));
        }

        return userMapper.entitiesToDTOs(users);
    }

    public String getDepartmentForUser(String username) throws FieldNotFoundException {

        String department = userRepository.findDepartmentByUsername(username);
        if (department.isEmpty()) {
            throw new FieldNotFoundException(departmentForUserNotFound(username));
        }

        return department;
    }

    public Map getCheckList(UserDTO userDTO) throws EntityNotFoundException {

        CheckList checkList = userRepository.getCheckListForUser(userRepository.findOne(userDTO.getIdUser()));

        if (checkList == null) {
            throw new EntityNotFoundException(checklistForUserNotFound(userDTO.getUsername()));
        }

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
    }

    public void saveCheckListForUser(String user, CheckListDTO checkList) {

        User userEntity = userRepository.findByUsername(user).get();
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
    public boolean deleteUser(String username) throws EntityNotFoundException, DatabaseException {

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

                CheckList checkListEntity = checkListRepository.findByUserAccount(userEntity);
                if (checkListEntity != null) {
                    checkListRepository.delete(checkListEntity);
                } else {
                    throw new EntityNotFoundException(checklistForUserNotFound(username));
                }

                LeaveCheckList leavecheckListEntity = leaveCheckListDAO.findLeaveCheckListByUser(userEntity);
                if (leavecheckListEntity != null) {
                    leaveCheckListDAO.deleteEntity(leavecheckListEntity);
                } else {
                    checklistForUserNotFound(username);
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
        } else {
            throw new EntityNotFoundException(userNotFound(username));
        }
    }

    public UserInformationDTO getUserInformationForUser(String username) throws EntityNotFoundException {

        User user = userRepository.findByUsername(username).get();
        if (user == null) {
            throw new EntityNotFoundException(userNotFound(username));
        }
        UserInformation userInformation = userInformationDAO.findUserInformationByUser(user);
        if (user == null) {
            throw new EntityNotFoundException("Information for user " + username + "not found");
        }

        return userInformationMapper.mapToDTO(userInformation);
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
            return checkListRepository.getValueForMailSent(user);
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

        return !(userRepository.findByUsername(username).isPresent() || userRepository.findByMsgMail(
                msgMail).isPresent());
    }
}
