package service;

import com.google.common.hash.Hashing;
import dao.UserDAO;
import dto.UserDTO;
import dto.mapper.UserMapper;
import entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;

@Service
public class UserService {

    @Autowired
    private UserDAO userDAO;


    private UserMapper userMapper = UserMapper.INSTANCE;

    public void createUser(UserDTO userDTO) {
        User user = userMapper.mapToNewEntity(userDTO);
        userDAO.persistEntity(user);
    }

    public UserDTO findUserByUsername(String username) {
        return userMapper.mapToDTO(userDAO.findUserByUsername(username));
    }


    public void addUser(UserDTO user) {
        user.setPassword(encrypt(user.getPassword()));
        userDAO.persistEntity(userMapper.mapToNewEntity(user));
    }


    public boolean checkUsername(String username) {
        if (findUserByUsername(username) != null)
            return false;
        return true;

    }

    public String encrypt(String initString) {
        return Hashing.sha256().hashString(initString, StandardCharsets.UTF_8).toString();
    }

    public boolean updateUser(UserDTO userUpdated) {

        User user = userDAO.findUserByUsername(userUpdated.getUsername());
        if (user != null) {
            userUpdated.setPassword(encrypt(userUpdated.getPassword()));

            User entity = userMapper.mapToEntity(userUpdated, user);
            userDAO.persistEntity(entity);
            return true;
        }


        return false;
    }


}
