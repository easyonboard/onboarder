package service;

import dao.CheckListRepository;
import dto.CheckListDto;
import dto.UserDto;
import dto.mapper.CheckListMapper;
import entity.CheckList;
import entity.User;
import exception.types.EntityNotFoundException;
import exception.types.InvalidDataException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CheckListService {

    @Autowired
    private CheckListRepository checkListRepository;

    private CheckListMapper checkListMapper = CheckListMapper.INSTANCE;

    public CheckListDto addCheckList(UserDto userInformationDto, User appUser) throws InvalidDataException {

        if (userInformationDto == null || appUser == null) {
            throw new InvalidDataException("Checklist data is invalid");
        }
        CheckList checkList = new CheckList();
        checkList.setHasBuddyAssigned(userInformationDto.getBuddyUser() != null);
        checkList.setUserAccount(appUser);
        checkListRepository.save(checkList);

        return checkListMapper.mapToDTO(checkListRepository.save(checkList));
    }

    public CheckListDto findByUser(User userEntity) throws EntityNotFoundException {
        CheckList checkList = checkListRepository.findByUserAccount(userEntity);
        if (checkList == null) {
            throw new EntityNotFoundException("checklist could not be found in the database");
        }
        return checkListMapper.mapToDTO(checkList);
    }

    public boolean isMailSentToUser(User userEntity) throws EntityNotFoundException {
        return this.findByUser(userEntity).isMailSent();
    }

    public void updateFieldMailSent(Integer idUser, boolean value){
        checkListRepository.updateFieldMailSent(idUser,value);
    }

    public void updateFieldMailSentToBuddy(Integer idUser, boolean value){
        checkListRepository.updateFieldMailSentToBuddy(idUser,value);
    }

}
