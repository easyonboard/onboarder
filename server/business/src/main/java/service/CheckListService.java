package service;

import dao.CheckListDAO;
import dao.CheckListRepository;
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
    private CheckListRepository checkListRepository;

    private CheckListMapper checkListMapper = CheckListMapper.INSTANCE;

    public CheckListDTO addCheckList(UserInformationDTO userInformationDTO, User appUser) {

        CheckList checkList = new CheckList();
        checkList.setHasBuddyAssigned(userInformationDTO.getBuddyUser()!=null);
        checkList.setUserAccount(appUser);
        checkListRepository.save(checkList);

        return checkListMapper.mapToDTO(checkListRepository.save(checkList));
    }

    public CheckListDTO findByUser(User userEntity) {
        return checkListMapper.mapToDTO(checkListRepository.findByUserAccount(userEntity));
    }

    public boolean isMailSentToUser(User userEntity) {
        return this.findByUser(userEntity).isMailSent();
    }

}
