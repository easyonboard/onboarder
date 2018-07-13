package service;

import dao.CheckListDAO;
import dto.CheckListDTO;
import dto.UserInformationDTO;
import dto.mapper.CheckListMapper;
import entity.CheckList;
import entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CheckListService {

    @Autowired
    private CheckListDAO checkListDAO;

    private CheckListMapper checkListMapper = CheckListMapper.INSTANCE;

    public CheckListDTO addCheckList(UserInformationDTO userInformationDTO, User appUser) {

        CheckList checkList = new CheckList();
        checkList.setHasBuddyAssigned(userInformationDTO.getBuddyUser()!=null);
        checkList.setUserAccount(appUser);
        checkListDAO.persistEntity(checkList);

        return checkListMapper.mapToDTO(checkListDAO.persistEntity(checkList));
    }

    public CheckListDTO findByUser(User userEntity) {
        return checkListMapper.mapToDTO(checkListDAO.findByUser(userEntity));
    }

    public boolean isMailSentToUser(User userEntity) {
        return this.findByUser(userEntity).isMailSent();
    }

}
