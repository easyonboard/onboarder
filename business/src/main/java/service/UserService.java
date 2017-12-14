package service;

import dao.UserDAO;
import dto.UserDTO;
import dto.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserDAO userDAO;


    private UserMapper userMapper= UserMapper.INSTANCE;

    public void createUser(UserDTO userDTO){
   //     User user = userMapper.mapToNewEntity(userDTO);
       // userDAO.persistEntity(user);
    }

    public UserDTO findUserByUsername(String username){
        return userMapper.mapToDTO(userDAO.findUserByUsername(username));
    }


    public void addUser(UserDTO user) {
        userDAO.persistEntity(userMapper.mapToNewEntity(user));
    }


    public boolean checkUsername(String username){
        if (findUserByUsername(username)!=null)
            return false;
        return true;

    }
}
