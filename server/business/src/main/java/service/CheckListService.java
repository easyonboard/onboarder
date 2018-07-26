package service;

import dao.CheckListRepository;
import dto.CheckListDTO;
import dto.UserInformationDTO;
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

    public CheckListDTO addCheckList(UserInformationDTO userInformationDTO, User appUser) throws InvalidDataException {

        if (userInformationDTO == null || appUser == null){
            throw new InvalidDataException("Checklist data is invalid");
        }
        CheckList checkList = new CheckList();
        checkList.setHasBuddyAssigned(userInformationDTO.getBuddyUser() != null);
        checkList.setUserAccount(appUser);
        checkListRepository.save(checkList);

        return checkListMapper.mapToDTO(checkListRepository.save(checkList));
    }

    public CheckListDTO findByUser(User userEntity) throws  EntityNotFoundException {
        CheckList checkList = checkListRepository.findByUserAccount(userEntity);
        if (checkList == null){
            throw new EntityNotFoundException("checklist could not be found in the database");
        }
        return checkListMapper.mapToDTO(checkList);
    }

    public boolean isMailSentToUser(User userEntity) throws  EntityNotFoundException{
        return this.findByUser(userEntity).isMailSent();
    }

}
