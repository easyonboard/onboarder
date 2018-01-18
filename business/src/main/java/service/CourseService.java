package service;

import dao.CourseDAO;
import dao.EnrollDAO;
import dao.SubjectDAO;
import dao.UserDAO;
import dto.CourseDTO;
import dto.SubjectDTO;
import dto.UserDTO;
import dto.mapper.CourseMapper;
import entity.Course;
import entity.Subject;
import entity.User;
import exception.CourseNotFoundException;
import exception.InvalidDataException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import validator.CourseValidator;

import java.util.List;

/**
 * Service for {@link CourseDTO}
 */
@Service
public class CourseService {
    @Autowired
    private EnrollDAO enrollDAO;

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private CourseDAO courseDAO;

    @Autowired
    private CourseValidator courseValidator;

    @Autowired
    private SubjectDAO subjectDAO;

    private static final String COURSE_NOT_FOUND="Course not found";
    private CourseMapper courseMapper = CourseMapper.INSTANCE;



    public void enrollUserToCourse(String username, Integer idCourse) {
        User userEntity = userDAO.findUserByUsername(username);
        Course courseEntity = courseDAO.findEntity(idCourse);
        enrollDAO.enrollUserToCourse(userEntity, courseEntity);

    }

    public List<CourseDTO> getAllCourses() {
        return courseMapper.entitiesToDTOs(courseDAO.allCourses());
    }

    public CourseDTO getCourseById(Integer id) throws CourseNotFoundException {
        Course entity = courseDAO.findEntity(id);
        if(entity==null)
            throw new CourseNotFoundException(COURSE_NOT_FOUND);
        return courseMapper.mapToDTO(entity);
    }

    public List<CourseDTO> searchByOverview(String overview) {
        return courseMapper.entitiesToDTOs(courseDAO.searchByOverview(overview));
    }

    public boolean userIsEnrolledOnCourse(String username, Integer idCourse) {
        User userEntity = userDAO.findUserByUsername(username);
        Course courseEntity = courseDAO.findEntity(idCourse);
        return (userEntity.getEnrolledCourses().contains(courseEntity));
    }

    public void unenrollUserToCourse(String username, Integer idCourse) {
        User userEntity = userDAO.findUserByUsername(username);
        Course courseEntity = courseDAO.findEntity(idCourse);
        enrollDAO.unenrollUserToCourse(userEntity, courseEntity);
    }

    public void updateCourse(CourseDTO course) throws InvalidDataException {

        if(course.getTitleCourse()==""){
            course.setTitleCourse(null);
        }
        Course courseEntity=courseMapper.mapToEntity(course,courseDAO.findEntity(course.getIdCourse()));
        courseValidator.validateCourseData(courseMapper.mapToDTO(courseEntity));
        courseDAO.persistEntity(courseEntity);
    }

    public void deleteContactPersonForCourse(UserDTO user, CourseDTO course) {

        Course courseEntity=courseDAO.findEntity(course.getIdCourse());
        User userEntity=userDAO.findEntity(user.getIdUser());


        courseEntity.getContactPersons().remove(userEntity);
        userEntity.getContactForCourses().remove(courseEntity);

        userDAO.persistEntity(userEntity);
        courseDAO.persistEntity(courseEntity);

    }

    public void deleteOwnerPersonForCourse(UserDTO user, CourseDTO course) {
        Course courseEntity=courseDAO.findEntity(course.getIdCourse());
        User userEntity=userDAO.findEntity(user.getIdUser());


        courseEntity.getOwners().remove(userEntity);
        userEntity.getOwnerForCourses().remove(courseEntity);

        userDAO.persistEntity(userEntity);
        courseDAO.persistEntity(courseEntity);
    }

    public void deleteSubjectFromCourse(CourseDTO courseDTO, SubjectDTO subjectDTO) {
        Course courseEntity=courseDAO.findEntity(courseDTO.getIdCourse());
        Subject subjectEntity=subjectDAO.findEntity(subjectDTO.getIdSubject());

        courseEntity.getSubjects().remove(subjectEntity);
        subjectEntity.getContainedByCourses().remove(courseEntity);

        courseDAO.persistEntity(courseEntity);
        subjectDAO.persistEntity(subjectEntity);
    }

    public CourseDTO addContactPerson(String email, CourseDTO course) {

        User userEntity=userDAO.findUserByEmail(email);
        Course courseEntity=courseDAO.findEntity(course.getIdCourse());

        courseEntity.getContactPersons().add(userEntity);
        userEntity.getContactForCourses().add(courseEntity);
        userDAO.persistEntity(userEntity);
        Course persisted=courseDAO.persistEntity(courseEntity);

        return courseMapper.mapToDTO(persisted);
    }

    public CourseDTO addOwnerPerson(String email, CourseDTO course) {
        User userEntity=userDAO.findUserByEmail(email);
        Course courseEntity=courseDAO.findEntity(course.getIdCourse());

        courseEntity.getOwners().add(userEntity);
        userEntity.getOwnerForCourses().add(courseEntity);
        userDAO.persistEntity(userEntity);
        Course persisted=courseDAO.persistEntity(courseEntity);

        return courseMapper.mapToDTO(persisted);
    }
}
