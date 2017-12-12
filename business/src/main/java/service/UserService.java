package service;

import dao.UserDAO;
import dto.UserDTO;
import dto.mapper.UserMapper;
import entity.User;
import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.Mapping;

import java.util.ArrayList;

@Service
public class UserService {

    @Autowired
    private UserDAO userDAO;


    private UserMapper userMapper= UserMapper.INSTANCE;

    public void createUser(UserDTO userDTO){
        if (null == userDTO.getContactForCourses()){
            userDTO.setContactForCourses(new ArrayList<>());
        }
        if (null == userDTO.getOwnerForCourses()){
            userDTO.setOwnerForCourses(new ArrayList<>());
        }
        if (null ==userDTO.getEnrolledCourses()){
            userDTO.setEnrolledCourses(new ArrayList<>());
        }
        User user = userMapper.mapToNewEntity(userDTO);
        userDAO.persistEntity(user);
    }



}
