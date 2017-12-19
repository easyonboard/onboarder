package service;

import dao.SubjectDAO;
import dto.SubjectDTO;
import dto.mapper.SubjectMapper;
import entity.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class SubjectService {

    @Autowired
    private SubjectDAO subjectDAO;

    private SubjectMapper subjectMapper = SubjectMapper.INSTANCE;


    public void createSubject(SubjectDTO subjectDTO) {
        Subject subject =subjectMapper.mapToNewEntity(subjectDTO);
        subjectDAO.persistEntity(subject);
    }
}
