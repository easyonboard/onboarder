package service;

import com.google.common.hash.Hashing;
import dao.UserDAO;
import dto.UserDTO;
import dto.mapper.UserMapper;
import entity.User;
import exception.InvalidDataException;
import exception.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import validator.UserValidator;

import java.nio.charset.StandardCharsets;

@Service
public class UserService {

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private UserValidator userValidator;

    private UserMapper userMapper = UserMapper.INSTANCE;

    private static final String USER_NOT_FOUND_ERROR = "User not found";
    public UserDTO findUserByUsername(String username) throws UserNotFoundException {

        User entity=userDAO.findUserByUsername(username);
        if(entity==null)
            throw new UserNotFoundException(USER_NOT_FOUND_ERROR);
        return userMapper.mapToDTO(entity);
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

        User user = userDAO.findUserByUsername(userUpdated.getUsername());
        if (user != null) {

            if (userUpdated.getPassword() != null) {
                userUpdated.setPassword(encrypt(userUpdated.getPassword()));
            }

            User entity = userMapper.mapToEntity(userUpdated, user);
            userValidator.validateUserData(userMapper.mapToDTO(entity));
            userDAO.persistEntity(entity);

        }
    }


}
