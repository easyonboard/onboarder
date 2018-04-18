package service;

import dao.UserInformationDAO;
import dto.UserInformationDTO;
import dto.mapper.UserInformationMapper;
import dto.mapper.UserMapper;
import entity.User;
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
                .updateUserInformation(userInformationMapper.mapToNewEntity(userInfo));

        userInformation.setTeam(userInfo.getTeam());
        userInformation.setBuilding(userInfo.getBuilding());
        userInformation.setStore(userInfo.getStore());
        userInformation.setBuddyUser(userMapper.mapToNewEntity(userInfo.getBuddyUser()));

//        UserInformation userInformation = userInformationDAO
//                .getUserInformationForUserAccount(userMapper.mapToNewEntity(userInfo.getUserAccount()));
//
//        userInformation.setTeam(userInfo.getTeam());
//        userInformation.setBuilding(userInfo.getBuilding());
//        userInformation.setFloor(userInfo.getFloor());
//        userInformation.setBuddyUser(userMapper.mapToNewEntity(userInfo.getBuddyUser()));
//
//        userInformationDAO.updateEntity(userInformation);
    }

    public void addUserInfo(UserInformationDTO userInfo) {
//        UserInformation userInformation = new UserInformation();
//
//        userInformation.setTeam(userInfo.getTeam());
//        userInformation.setBuilding(userInfo.getBuilding());
//        userInformation.setStore(userInfo.getStore());
//        userInformation.setDepartment(userInfo.getDepartment());
//        userInformation.setMailSent(userInfo.getMailSent());
//        userInformation.setProject(userInfo.getProject());
//        userInformation.setStartDate(userInfo.getStartDate());
//
//        User user = new User();
//
//        userInformation.setBuddyUser(userMapper.mapToEntity(userInfo.getBuddyUser(), user));
//        userInformation.setUserAccount(userMapper.mapToEntity(userInfo.getUserAccount(), user));
//
//        userInformationDAO.persistEntity(userInformation);
    }
}
