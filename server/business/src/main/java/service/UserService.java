package service;

import com.google.common.hash.Hashing;
import dao.CheckListDAO;
import dao.UserDAO;
import dao.UserInformationDAO;
import dto.CheckListDTO;
import dto.UserDTO;
import dto.UserInformationDTO;
import dto.mapper.CheckListMapper;
import dto.mapper.UserInformationMapper;
import dto.mapper.UserMapper;
import entity.CheckList;
import entity.User;
import exception.InvalidDataException;
import exception.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import validator.UserValidator;

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

    private UserMapper userMapper = UserMapper.INSTANCE;

    private CheckListMapper checkListMapper = CheckListMapper.INSTANCE;

    private UserInformationMapper userInformationMapper = UserInformationMapper.INSTANCE;

    private static final String USER_NOT_FOUND_ERROR = "User not found";

    public UserDTO findUserByUsername(String username) throws UserNotFoundException {

        Optional<User> entity = userDAO.findUserByUsername(username);
        if (!entity.isPresent()) {
            throw new UserNotFoundException(USER_NOT_FOUND_ERROR);
        }
        return userMapper.mapToDTO(entity.get());
    }


    public void addUser(UserDTO userDTO) throws InvalidDataException {
        userValidator.validateUsername(userDTO.getUsername());
        userValidator.validateUserData(userDTO);
        userDTO.setPassword(encrypt(userDTO.getPassword()));
        User user = new User();
        userDAO.persistEntity(userMapper.mapToEntity(userDTO, user));
    }

    public void addUserInfo(UserInformationDTO userInfo) {
        userInformationDAO.persistEntity(userInformationMapper.mapToNewEntity(userInfo));
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
        return userInformationMapper.entitiesToDTOs(userInformationDAO.getAllNewUsers());
    }

    public List<UserDTO> searchByUsername(String name) {
        return userMapper.entitiesToDTOs(userDAO.searchByUsername(name));
    }

    public Map getCheckList(UserDTO userDTO) {
        return userDAO.getCheckListMapForUser(userDAO.findEntity(userDTO.getIdUser()));
    }

    public void saveCheckListForUser(String user, CheckListDTO checkList) {
        User userEntity = userDAO.findUserByUsername(user).get();
        checkList.setUserAccount(userEntity);
        CheckList checkListEntity = checkListDAO.findByUser(userEntity);
        checkListMapper.mapToEntity(checkList, checkListEntity);
        checkListDAO.persistEntity(checkListEntity);
    }

//    public List<UserDTO> searchByName(String name){
////        userMapper.entitiesToDTOs(userDAO.searchByName(name));
//return null;
//    }

}
