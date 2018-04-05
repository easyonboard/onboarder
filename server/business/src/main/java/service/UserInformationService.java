package service;

import dao.UserInformationDAO;
import dto.UserInformationDTO;
import dto.mapper.UserInformationMapper;
import dto.mapper.UserMapper;
import entity.UserInformation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserInformationService {

    @Autowired
    private UserInformationDAO userInformationDAO;

    private UserInformationMapper userInformationMapper = UserInformationMapper.INSTANCE;
    private UserMapper userMapper = UserMapper.INSTANCE;

    public void updateUserInfo(UserInformationDTO userInfo) {
        UserInformation userInformation = userInformationDAO
                .getUserInformationForUserAccount(userMapper.mapToNewEntity(userInfo.getUserAccount()));

        userInformationDAO.updateEntity(userInformation);
    }
}
