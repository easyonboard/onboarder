package service;

import com.google.common.hash.Hashing;
import dao.*;
import dto.CheckListDTO;
import dto.LeaveCheckListDTO;
import dto.UserDTO;
import dto.UserInformationDTO;
import dto.mapper.CheckListMapper;
import dto.mapper.LeaveCheckListMapper;
import dto.mapper.UserInformationMapper;
import dto.mapper.UserMapper;
import entity.*;
import entity.enums.DepartmentType;
import exception.types.DatabaseException;
import exception.types.EntityNotFoundException;
import exception.types.FieldNotFoundException;
import exception.types.InvalidDataException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import validator.UserValidator;

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
    private LeaveCheckListRepository leaveCheckListRepository;

    @Autowired
    private UserInformationService userInformationService;

    @Autowired
    private CheckListService checkListService;

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private TutorialRepository tutorialRepository;

    private UserMapper userMapper = UserMapper.INSTANCE;

    private CheckListMapper checkListMapper = CheckListMapper.INSTANCE;

    private LeaveCheckListMapper leaveCheckListMapper = LeaveCheckListMapper.INSTANCE;

    private UserInformationMapper userInformationMapper = UserInformationMapper.INSTANCE;

    public UserDTO findUserByUsername(String username) throws EntityNotFoundException {

        Optional<User> entity = userRepository.findByUsername(username);
        if (!entity.isPresent()) {
            throw new EntityNotFoundException(userNotFound(username));
        }
        return userMapper.mapToDTO(entity.get());
    }

    public void addUser(UserDTO userDTO,
                        UserInformationDTO userInformationDTO) throws InvalidDataException, DatabaseException {

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

    public void updateUser(
            UserDTO userUpdated) throws InvalidDataException, EntityNotFoundException, DatabaseException {

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

    public List<UserDTO> getUsersInDepartmentForUser(
            String username) throws EntityNotFoundException, FieldNotFoundException {

        String department = getDepartmentForUser(username);
        DepartmentType departmentType = DepartmentType.valueOf(department);
        List<User> users = userRepository.findAllByDepartment(departmentType);
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

        User user = userRepository.findOne(userDTO.getIdUser());
        CheckList checkList = userRepository.getCheckListForUser(user);

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

    public void saveCheckListForUser(String username,
                                     CheckListDTO checkList) throws EntityNotFoundException, DatabaseException {

        User userEntity = userRepository.findByUsername(username).get();
        if (userEntity == null) {
            throw new EntityNotFoundException(userNotFound(username));
        }

        checkList.setUserAccount(userMapper.mapToDTO(userEntity));
        CheckList checkListEntity = checkListRepository.findByUserAccount(userEntity);
        if (checkListEntity == null) {
            throw new EntityNotFoundException(checklistForUserNotFound(username));
        }

        checkListMapper.mapToEntity(checkList, checkListEntity);
        if (checkListRepository.save(checkListEntity) == null) {
            throw new DatabaseException(checklistForUserNotSaved(username));
        }
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

                LeaveCheckList leavecheckListEntity = leaveCheckListRepository.findLeaveCheckListByUserAccount(
                        userEntity);
                if (leavecheckListEntity != null) {
                    leaveCheckListRepository.delete(leavecheckListEntity);
                } else {
                    checklistForUserNotFound(username);
                }

                List<Tutorial> tutorialsForUser = tutorialRepository.getTutorialsForUser(userEntity);
                for (Tutorial aTutorialsForUser : tutorialsForUser) {
                    aTutorialsForUser.getContactPersons().remove(userEntity);
                    tutorialRepository.save(aTutorialsForUser);

                }
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

    public List<String> getAllUserames() throws EntityNotFoundException {

        List<User> users = userRepository.findAll();
        if (users.isEmpty()) {
            throw new EntityNotFoundException(USERS_NOT_FOUND_EXCEPTION);
        }
        List<String> usersList = new ArrayList<>();

        users.forEach(user -> usersList.add(user.getMsgMail()));

        return usersList;
    }

    public void updateUserPassword(String username, String password) throws EntityNotFoundException, DatabaseException {

        Optional<User> userOptional = userRepository.findByUsername(username);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            if (password != null) {
                user.setPassword(encrypt(password));
            }
            if (userRepository.save(user) == null) {
                throw new DatabaseException(USER_SAVE_DATABASE_EXCEPTION);
            }
        }

    }

    public boolean getStatusMailForUser(String username) throws EntityNotFoundException {

        Optional<User> userOptional = userRepository.findByUsername(username);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            return checkListRepository.getValueForMailSent(user);
        } else {
            throw new EntityNotFoundException(userNotFound(username));
        }
    }

    public LeaveCheckListDTO getLeaveCheckListForUser(String username) throws EntityNotFoundException {

        Optional<User> user = userRepository.findByUsername(username);
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

    public LeaveCheckListDTO saveLeaveCheckList(LeaveCheckListDTO leaveCheckListDTO) throws DatabaseException {

        LeaveCheckList leaveCheckList = new LeaveCheckList();
        leaveCheckListMapper.mapToEntity(leaveCheckListDTO, leaveCheckList);
        if (leaveCheckListRepository.save(leaveCheckList) == null) {
            throw new DatabaseException("Leave check list for user could not be updated");
        }
        return leaveCheckListMapper.mapToDTO(leaveCheckListRepository.findOne(leaveCheckListDTO.getIdCheckList()));
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

        return !(userRepository.findByUsername(username).isPresent() || userRepository.findByMsgMail(
                msgMail).isPresent());
    }
}
