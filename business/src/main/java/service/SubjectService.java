package service;

import dao.CourseDAO;
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

    @Autowired
    private CourseDAO courseDAO;

    private SubjectMapper subjectMapper = SubjectMapper.INSTANCE;


    public void createSubject(SubjectDTO subjectDTO) {
        Subject subject =subjectMapper.mapToNewEntity(subjectDTO);
        subjectDAO.persistEntity(subject);
    }

    public SubjectDTO findSubjectOfCourse(Integer id, Integer idSubject) {
        Subject subjectEntity=subjectDAO.findSubjectFromCourse(id,idSubject);
        return subjectMapper.mapToDTO(subjectEntity);
    }
}
