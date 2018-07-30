package service;

import dao.UserInformationRepository;
import dao.UserRepository;
import dto.UserInformationDTO;
import dto.mapper.UserInformationMapper;
import entity.User;
import entity.UserInformation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserInformationService {


    @Autowired
    private UserInformationRepository userInformationRepository;

    @Autowired
    UserRepository userRepository;


    private UserInformationMapper userInformationMapper = UserInformationMapper.INSTANCE;


    public void updateUserInfo(UserInformationDTO userInfo) {

        UserInformation actualUserInfo = userInformationRepository.findOne(userInfo.getIdUserInformation());

        actualUserInfo.setTeam(userInfo.getTeam());
        actualUserInfo.setLocation(userInfo.getLocation());
        actualUserInfo.setFloor(userInfo.getFloor());
        actualUserInfo.setProject(userInfo.getProject());

        actualUserInfo.setDepartment(userInfo.getDepartment());
        actualUserInfo.setStartDate(userInfo.getStartDate());

        if (userInfo.getBuddyUser().getUsername() != null) {
            User newUser = userRepository.findByUsername(userInfo.getBuddyUser().getUsername()).get();
            actualUserInfo.setBuddyUser(newUser);
        }

        userInformationRepository.save(actualUserInfo);
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

        if (buddyUser != null) {
            userInformation.setBuddyUser(buddyUser);
        }

        userInformationRepository.save(userInformation);
    }

}
