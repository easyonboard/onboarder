package service;

import dao.UserInformationDAO;
import dto.UserInformationDTO;
import dto.mapper.UserInformationMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserInformationService {

    @Autowired
    private UserInformationDAO userInformationDAO;

    private UserInformationMapper userInformationMapper = UserInformationMapper.INSTANCE;

    public void addUser(UserInformationDTO userInfo) {
        userInformationDAO.persistEntity(userInformationMapper.mapToNewEntity(userInfo));
    }
}
