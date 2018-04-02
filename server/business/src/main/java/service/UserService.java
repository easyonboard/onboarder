package service;

import com.google.common.hash.Hashing;
import dao.UserDAO;
import dao.UserInformationDAO;
import dto.UserDTO;
import dto.UserInformationDTO;
import dto.mapper.UserInformationMapper;
import dto.mapper.UserMapper;
import entity.User;
import exception.InvalidDataException;
import exception.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

    private UserMapper userMapper = UserMapper.INSTANCE;

    private UserInformationMapper userInformationMapper = UserInformationMapper.INSTANCE;

    private static final String USER_NOT_FOUND_ERROR = "User not found";
    public UserDTO findUserByUsername(String username) throws UserNotFoundException {

        Optional<User> entity=userDAO.findUserByUsername(username);
        if(!entity.isPresent()) {
            throw new UserNotFoundException(USER_NOT_FOUND_ERROR);
        }
        return userMapper.mapToDTO(entity.get());
    }


    public void addUser(UserDTO user) throws InvalidDataException {
        userValidator.validateUsername(user.getUsername());
        userValidator.validateUserData(user);
        user.setPassword(encrypt(user.getPassword()));
        userDAO.persistEntity(userMapper.mapToNewEntity(user));
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

    public Map getCheckList(UserDTO userDTO) {
        return userDAO.getCheckListForUser(userDAO.findEntity(userDTO.getIdUser()));
    }

//    public List<UserDTO> searchByName(String name){
////        userMapper.entitiesToDTOs(userDAO.searchByName(name));
//return null;
//    }

}
