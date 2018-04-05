package service;

import dao.CheckListDAO;

import dto.CheckListDTO;
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

    public CheckListDTO addCheckList(CheckListDTO checkListDTO) {
        CheckList checkList = checkListMapper.mapToEntity(checkListDTO, new CheckList());

        return checkListMapper.mapToDTO(checkListDAO.persistEntity(checkList));
    }

    public CheckList findByUser(User userEntity) {
        return checkListDAO.findByUser(userEntity);
    }

}
