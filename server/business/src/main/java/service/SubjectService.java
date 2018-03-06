package service;

import dao.CourseDAO;
import dao.SubjectDAO;
import dao.UserDAO;
import dao.User_SubjectDAO;
import dto.CourseDTO;
import dto.SubjectDTO;
import dto.UserDTO;
import dto.mapper.SubjectMapper;
import entity.Course;
import entity.Subject;
import entity.User;
import entity.User_Subject;
import exception.InvalidDataException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.UserDataHandler;
import validator.SubjectValidator;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

/**
 * Service for {@link SubjectDTO}
 */
@Service
public class SubjectService {

    @Autowired
    private SubjectDAO subjectDAO;

    @Autowired
    private CourseDAO courseDAO;

    @Autowired
    private SubjectValidator subjectValidator;

    @Autowired
    private User_SubjectDAO user_subjectDAO;

    @Autowired
    private UserDAO userDAO;


    private SubjectMapper subjectMapper = SubjectMapper.INSTANCE;




    public SubjectDTO findSubjectOfCourse(Integer id, Integer idSubject) {
        Subject subjectEntity=subjectDAO.findSubjectFromCourse(id,idSubject);
        return subjectMapper.mapToDTO(subjectEntity);
    }

    public SubjectDTO addSubject(SubjectDTO subjectDTO, CourseDTO courseDTO) throws InvalidDataException {
        subjectValidator.validateSubject(subjectDTO);
        Subject subject =subjectMapper.mapToNewEntity(subjectDTO);
        Course course = courseDAO.findEntity(courseDTO.getIdCourse());

        subject.setContainedByCourse(course);
        List<Subject> subjects = course.getSubjects();
        subjects.add(subject);
        course.setSubjects(subjects);
        courseDAO.persistEntity(course);
        return subjectMapper.mapToDTO(subjectDAO.persistEntity(subject));
    }

    public int markAsFinished(SubjectDTO subject, UserDTO user) {
        User userEntity=userDAO.findUserByUsername(user.getUsername()).get();
        Subject subjectEntity=subjectDAO.findEntity(subject.getIdSubject());

        User_Subject user_subject=user_subjectDAO.findEntityByUserAndSubject(userEntity,subjectEntity);


        user_subject.setStatus(true);
        user_subjectDAO.persistEntity(user_subject);
        Subject newSubject=subjectDAO.findNextSubject(subjectEntity);
        if(newSubject!=null ){
            if(user_subjectDAO.findEntityByUserAndSubject(userEntity, newSubject)==null) {
                User_Subject newUserSubject = new User_Subject();
                newUserSubject.setStatus(false);
                newUserSubject.setSubject(newSubject);
                newUserSubject.setCourse(subjectEntity.getContainedByCourse());
                newUserSubject.setUser(userEntity);
                user_subjectDAO.persistEntity(newUserSubject);

            }
            return newSubject.getIdSubject();
        }

        else{
            return 0;
        }
    }
}
