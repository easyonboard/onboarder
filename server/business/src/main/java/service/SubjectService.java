package service;

import dao.CourseDAO;
import dao.SubjectDAO;
import dto.CourseDTO;
import dto.SubjectDTO;
import dto.mapper.SubjectMapper;
import entity.Course;
import entity.Subject;
import exception.InvalidDataException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import validator.SubjectValidator;

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

    private SubjectMapper subjectMapper = SubjectMapper.INSTANCE;




    public SubjectDTO findSubjectOfCourse(Integer id, Integer idSubject) {
        Subject subjectEntity=subjectDAO.findSubjectFromCourse(id,idSubject);
        return subjectMapper.mapToDTO(subjectEntity);
    }

    public SubjectDTO addSubject(SubjectDTO subjectDTO, CourseDTO courseDTO) throws InvalidDataException {
        subjectValidator.validateSubject(subjectDTO);
        Subject subject =subjectMapper.mapToNewEntity(subjectDTO);
        Course course = courseDAO.findEntity(courseDTO.getIdCourse());
        List<Course> courses = new ArrayList<>();
        courses.add(course);
        subject.setContainedByCourses(courses);
        List<Subject> subjects = course.getSubjects();
        subjects.add(subject);
        course.setSubjects(subjects);
        courseDAO.persistEntity(course);
        return subjectMapper.mapToDTO(subjectDAO.persistEntity(subject));
    }
}
