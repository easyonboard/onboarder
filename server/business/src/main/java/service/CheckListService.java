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
import org.springframework.stereotype.Service;

@Service
public class CheckListService {

    @Autowired
    private CheckListRepository checkListRepository;

    private CheckListMapper checkListMapper = CheckListMapper.INSTANCE;

    public CheckListDto addCheckList(User appUser) throws InvalidDataException {
        CheckList checkList = new CheckList();
        checkList.setHasBuddyAssigned(appUser.getMateUsername() != null);
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

    public void updateFieldMailSentToBuddy(Integer idUser, boolean value){
        checkListRepository.updateFieldMailSentToBuddy(idUser,value);
    }

    public void updateFieldMailSent(Integer idUser, boolean value){
        checkListRepository.updateFieldMailSentToNewEmployee(idUser,value);
    }

}
