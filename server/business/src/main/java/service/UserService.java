package service;

import com.google.common.hash.Hashing;
import dao.*;
import dto.*;
import dto.mapper.*;
import entity.CheckList;
import entity.LeaveCheckList;
import entity.User;
import entity.UserInformation;
import exception.DataNotFoundException;
import exception.InvalidDataException;
import exception.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import validator.UserValidator;

import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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
    private CheckListDAO checkListDAO;

    @Autowired
    private LeaveCheckListDAO leaveCheckListDAO;

    @Autowired
    private UserInformationService userInformationService;

    @Autowired
    private CheckListService checkListService;


    @Autowired
    private LocationDAO locationDAO;

    @Autowired
    private TutorialDAO tutorialDAO;

    private UserMapper userMapper = UserMapper.INSTANCE;

    private CheckListMapper checkListMapper = CheckListMapper.INSTANCE;


    private LeaveCheckListMapper leaveCheckListMapper = LeaveCheckListMapper.INSTANCE;


    private UserInformationMapper userInformationMapper = UserInformationMapper.INSTANCE;

    private LocationMapper locationMapper = LocationMapper.INSTANCE;

    private static final String USER_NOT_FOUND_ERROR = "User not found";

    public UserDTO findUserByUsername(String username) throws UserNotFoundException {

        Optional<User> entity = userDAO.findUserByUsername(username);
        if (!entity.isPresent()) {
            throw new UserNotFoundException(USER_NOT_FOUND_ERROR);
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

    public void updateUser(UserDTO userUpdated) throws InvalidDataException {
        Optional<User> user = userDAO.findUserByUsername(userUpdated.getUsername());

        if (user.isPresent()) {
            if (userUpdated.getPassword() != null) {
                userUpdated.setPassword(encrypt(userUpdated.getPassword()));
            }
            User entity = userMapper.mapToEntity(userUpdated, user.get());
            userValidator.validateUserData(userMapper.mapToDTO(entity));
            userDAO.persistEntity(entity);
        }
    }

    public List<UserDTO> getAllUsers() {
        List<User> allUsersFromDb = userDAO.getAllUsers();
        return userMapper.entitiesToDTOs(allUsersFromDb);
    }

    public List<UserInformationDTO> getAllNewUsers() {
        List<UserInformation> list = userInformationDAO.getAllNewUsers();
        return userInformationMapper.entitiesToDTOs(userInformationDAO.getAllNewUsers());
    }

    public List<UserDTO> searchByName(String name) {
        return userMapper.entitiesToDTOs(userDAO.searchByName(name));
    }

    public List<UserDTO> getUsersInDepartmentForLoggedInUser(String username) {
        String department = getDepartmentForLoggedUser(username);
        return userMapper.entitiesToDTOs(userDAO.getUsersInDepartment(department));
    }

    public String getDepartmentForLoggedUser(String username) {
        return userDAO.getDepartmentForLoggedUser(username);
    }

    public Map getCheckList(UserDTO userDTO) {
        return userDAO.getCheckListMapForUser(userDAO.findEntity(userDTO.getIdUser()));
    }

    public void saveCheckListForUser(String user, CheckListDTO checkList) {
        User userEntity = userDAO.findUserByUsername(user).get();
        checkList.setUserAccount(userMapper.mapToDTO(userEntity));
        CheckList checkListEntity = checkListDAO.findByUser(userEntity);
        checkListMapper.mapToEntity(checkList, checkListEntity);
        checkListDAO.persistEntity(checkListEntity);
    }


    /**
     *
     * @param username
     * @return
     * @throws UserNotFoundException
     * delete user and all children from db
     * searches all children and deletes them one by one
     * searches all other users who have the user to be deleted as buddy and sets the buddy field TO NULL
     *
     *
     * TO DO refactoring
     *
     *
     */
    public boolean deleteUser(String username) throws UserNotFoundException {

        Optional<User> userOptional = userDAO.findUserByUsername(username);
        if (userOptional.isPresent()) {
            User userEntity = userOptional.get();

            if (canUserBeDeleted(userEntity)) {

                UserInformation userInformationEntity = userInformationDAO.findUserInformationByUser(userEntity);
                if (userInformationEntity != null) {
                    userInformationEntity.setBuddyUser(null);
                    userInformationEntity
                            .setLocation(null);

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
                userInformationDAO.setBuddyToNull(userEntity);
                userDAO.deleteEntity(userEntity);
                return true;
            } else {
                return false;
            }

        } else throw new UserNotFoundException(USER_NOT_FOUND_ERROR);

    }


    public UserInformationDTO getUserInformationForUser(String username) {
        return userInformationMapper.mapToDTO(userInformationDAO.findUserInformationByUser(userDAO.findUserByUsername(username).get()));
    }

    public void updateUserPassword(String username, String password) {
        Optional<User> userOptional = userDAO.findUserByUsername(username);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            if (password != null) {
                user.setPassword(encrypt(password));
            }
            userDAO.persistEntity(user);
        }

    }

    public boolean getStatusMailForUser(String username) {
        Optional<User> userOptional = userDAO.findUserByUsername(username);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            return checkListDAO.getValueForMailSent(user);
        }
        return false;
    }

    public LeaveCheckListDTO getLeaveCheckListForUser(String username) throws UserNotFoundException {

        Optional<User> user = userDAO.findUserByUsername(username);
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
            throw new UserNotFoundException(USER_NOT_FOUND_ERROR);


    }

    public LeaveCheckListDTO saveLeaveCheckList(LeaveCheckListDTO leaveCheckList) {

        LeaveCheckList persistEntity = new LeaveCheckList();
        leaveCheckListMapper.mapToEntity(leaveCheckList, persistEntity);
        leaveCheckListDAO.update(persistEntity);
        return leaveCheckListMapper.mapToDTO(leaveCheckListDAO.findEntity(leaveCheckList.getIdCheckList()));
    }

    public boolean canUserBeDeleted(User user) {

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

    public List<LocationDTO> getAllLocations() {

        return locationMapper.entitiesToDTOs(locationDAO.getAllLocations());

    }
}
