package service;

import dao.UserInformationDAO;
import dto.UserInformationDTO;
import dto.mapper.UserInformationMapper;
import entity.User;
import entity.UserInformation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserInformationService {

    @Autowired
    private UserInformationDAO userInformationDAO;



    private UserInformationMapper userInformationMapper = UserInformationMapper.INSTANCE;


    public void updateUserInfo(UserInformationDTO userInfo) {

        userInformationDAO
                .updateUserInformation(userInformationMapper.mapToNewEntity(userInfo));

    }

    public void addUserInfo(UserInformationDTO userInformationDTO, User appUser, User buddyUser) {
        UserInformation userInformation = new UserInformation();

        userInformation.setTeam(userInformationDTO.getTeam());
        userInformation.setLocation(userInformationDTO.getLocation());
        userInformation.setFloor(userInformationDTO.getFloor());
        userInformation.setDepartment(userInformationDTO.getDepartment());
        userInformation.setProject(userInformationDTO.getProject());
        userInformation.setStartDate(userInformationDTO.getStartDate());
        userInformation.setUserAccount(appUser);

        if (buddyUser != null)
        {
            userInformation.setBuddyUser(buddyUser);
        }

        userInformationDAO.persistEntity(userInformation);
    }

}
