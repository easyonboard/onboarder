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
        userInformation.setFloor(userInfo.getFloor());
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

    public void addUserInfo(UserInformationDTO userInformationDTO, User appUser, User buddyUser) {
        UserInformation userInformation = new UserInformation();

        userInformation.setTeam(userInformationDTO.getTeam());
        userInformation.setBuilding(userInformationDTO.getBuilding());
        userInformation.setFloor(userInformationDTO.getFloor());
        userInformation.setDepartment(userInformationDTO.getDepartment());
        userInformation.setMailSent(userInformationDTO.getMailSent());
        userInformation.setProject(userInformationDTO.getProject());
        userInformation.setStartDate(userInformationDTO.getStartDate());
        userInformation.setUserAccount(appUser);
        userInformation.setBuddyUser(buddyUser);

        userInformationDAO.persistEntity(userInformation);
    }

}
