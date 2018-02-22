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
import exception.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import validator.CourseValidator;

import javax.persistence.NoResultException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    private static final String COURSE_NOT_FOUND = "Course not found";
    private CourseMapper courseMapper = CourseMapper.INSTANCE;


    public void enrollUserToCourse(String username, Integer idCourse) throws UserNotFoundException {

        Optional<User> userEntity = userDAO.findUserByUsername(username);
        if (userEntity.isPresent()) {
            Course courseEntity = courseDAO.findEntity(idCourse);
            enrollDAO.enrollUserToCourse(userEntity.get(), courseEntity);
        } else {
            throw new UserNotFoundException("User  not  found");
        }
    }

    public List<CourseDTO> getAllCourses() {
        return courseMapper.entitiesToDTOs(courseDAO.allCourses());
    }

    public CourseDTO getCourseById(Integer id) throws CourseNotFoundException {
        Course entity = courseDAO.findEntity(id);
        if (entity == null)
            throw new CourseNotFoundException(COURSE_NOT_FOUND);
        return courseMapper.mapToDTO(entity);
    }

    public List<CourseDTO> searchByOverview(String overview) {
        return courseMapper.entitiesToDTOs(courseDAO.searchByOverview(overview));
    }

    public boolean userIsEnrolledOnCourse(String username, Integer idCourse) throws UserNotFoundException {
        Optional<User> userEntity = userDAO.findUserByUsername(username);
        if (userEntity.isPresent()) {
            Course courseEntity = courseDAO.findEntity(idCourse);
            return (userEntity.get().getEnrolledCourses().contains(courseEntity));
        } else {
            throw new UserNotFoundException("User  not  found");
        }
    }

    public void unenrollUserToCourse(String username, Integer idCourse) throws UserNotFoundException {

        Optional<User> userEntity = userDAO.findUserByUsername(username);
        if (userEntity.isPresent()) {
            Course courseEntity = courseDAO.findEntity(idCourse);
            enrollDAO.unenrollUserToCourse(userEntity.get(), courseEntity);
        } else {
            throw new UserNotFoundException("User not found");
        }

    }

    public void updateCourse(CourseDTO course, List<Integer> ownersIds, List<Integer> contactPersonsId) throws InvalidDataException {

        if (course.getTitleCourse() == "") {
            course.setTitleCourse(null);
        }

        Course courseEntity = courseMapper.mapToEntity(course, courseDAO.findEntity(course.getIdCourse()));
        if(contactPersonsId!=null && contactPersonsId.size()>0){
            List<User> contanctPerson = new ArrayList<>();
            contactPersonsId.forEach(id->contanctPerson.add(userDAO.findEntity(id)));
            for(int i=0;i<contanctPerson.size();i++) {
                if (!courseEntity.getContactPersons().contains(contanctPerson.get(i))) {
                    courseEntity.getContactPersons().add(contanctPerson.get(i));
                }
            }
        }


        if(ownersIds!=null && ownersIds.size()>0){
            List<User> owners = new ArrayList<>();
            ownersIds.forEach(id->owners.add(userDAO.findEntity(id)));
            for(int i=0;i<owners.size();i++) {
                if (!courseEntity.getOwners().contains(owners.get(i))) {
                    courseEntity.getOwners().add(owners.get(i));
                }
            }
        }

        courseValidator.validateCourseData(courseMapper.mapToDTO(courseEntity));
        courseDAO.persistEntity(courseEntity);
    }

    public void deleteContactPersonForCourse(UserDTO user, CourseDTO course) {

        Course courseEntity = courseDAO.findEntity(course.getIdCourse());
        User userEntity = userDAO.findEntity(user.getIdUser());


        courseEntity.getContactPersons().remove(userEntity);
        userEntity.getContactForCourses().remove(courseEntity);

        userDAO.persistEntity(userEntity);
        courseDAO.persistEntity(courseEntity);

    }

    public void deleteOwnerPersonForCourse(UserDTO user, CourseDTO course) {
        Course courseEntity = courseDAO.findEntity(course.getIdCourse());
        User userEntity = userDAO.findEntity(user.getIdUser());


        courseEntity.getOwners().remove(userEntity);
        userEntity.getOwnerForCourses().remove(courseEntity);

        userDAO.persistEntity(userEntity);
        courseDAO.persistEntity(courseEntity);
    }

    public void deleteSubjectFromCourse(CourseDTO courseDTO, SubjectDTO subjectDTO) {
        Course courseEntity = courseDAO.findEntity(courseDTO.getIdCourse());
        Subject subjectEntity = subjectDAO.findEntity(subjectDTO.getIdSubject());

        courseEntity.getSubjects().remove(subjectEntity);
        subjectEntity.getContainedByCourses().remove(courseEntity);

        courseDAO.persistEntity(courseEntity);
        subjectDAO.persistEntity(subjectEntity);
    }

    public CourseDTO addContactPerson(String email, CourseDTO course) throws UserNotFoundException {

        Optional<User> userEntity = userDAO.findUserByEmail(email);
        if(userEntity.isPresent()) {
            Course courseEntity = courseDAO.findEntity(course.getIdCourse());

            courseEntity.getContactPersons().add(userEntity.get());
            userEntity.get().getContactForCourses().add(courseEntity);
            userDAO.persistEntity(userEntity.get());
            Course persisted = courseDAO.persistEntity(courseEntity);

            return courseMapper.mapToDTO(persisted);
        }else {
            throw new UserNotFoundException("User not found!");
        }
    }

    public CourseDTO addOwnerPerson(String email, CourseDTO course) throws UserNotFoundException {
        Optional<User> userEntity = userDAO.findUserByEmail(email);
        if(userEntity.isPresent()) {
            Course courseEntity = courseDAO.findEntity(course.getIdCourse());

            courseEntity.getOwners().add(userEntity.get());
            userEntity.get().getOwnerForCourses().add(courseEntity);
            userDAO.persistEntity(userEntity.get());
            Course persisted = courseDAO.persistEntity(courseEntity);

            return courseMapper.mapToDTO(persisted);
        }else {
            throw new UserNotFoundException("User not found!");
        }
    }

    public CourseDTO addCourse(CourseDTO courseDTO, List<Integer> ownersIds, List<Integer> contactPersonsId) throws InvalidDataException {
        courseValidator.validateCourseData(courseDTO);
        Course course = courseMapper.mapToEntity(courseDTO, new Course());
        List<User> owners = new ArrayList<>();
        ownersIds.forEach(ownerId->owners.add(userDAO.findEntity(ownerId)));

        List<User> constantPerson = new ArrayList<>();
        contactPersonsId.forEach(cpId->constantPerson.add(userDAO.findEntity(cpId)));

        course.setOwners(owners);
        course.setContactPersons(constantPerson);

        return courseMapper.mapToDTO(courseDAO.persistEntity(course));
    }
}
