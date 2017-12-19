package service;

import com.google.common.hash.Hashing;
import dao.UserDAO;
import dto.SubjectDTO;
import dto.UserDTO;
import dto.mapper.UserMapper;
import entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserDAO userDAO;


    private UserMapper userMapper= UserMapper.INSTANCE;

    public void createUser(UserDTO userDTO){
        User user = userMapper.mapToNewEntity(userDTO);
        userDAO.persistEntity(user);
    }

    public UserDTO findUserByUsername(String username){
        return userMapper.mapToDTO(userDAO.findUserByUsername(username));
    }


    public void addUser(UserDTO user) {
        user.setPassword(encrypt(user.getPassword()));
        userDAO.persistEntity(userMapper.mapToNewEntity(user));
    }


    public boolean checkUsername(String username){
        if (findUserByUsername(username)!=null)
            return false;
        return true;

    }

    public void updateUser(UserDTO userDTO) {
        User user = userDAO.findEntity(userDTO.getIdUser());
        userMapper.mapToEntity(userDTO,user);
        userDAO.persistEntity(user);
    }

    public String encrypt(String initString){
        return Hashing.sha256().hashString(initString, StandardCharsets.UTF_8).toString();
    }



}
