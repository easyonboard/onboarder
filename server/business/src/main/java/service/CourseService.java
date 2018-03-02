package service;

import dao.*;
import dto.CourseDTO;
import dto.SubjectDTO;
import dto.UserDTO;
import dto.mapper.CourseMapper;
import entity.Course;
import entity.Subject;
import entity.User;
import exception.CourseNotFoundException;
import exception.DeleteCourseException;
import exception.InvalidDataException;
import exception.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import validator.CourseValidator;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    @Autowired
    private User_SubjectDAO user_subjectDAO;

    private static final String COURSE_NOT_FOUND = "Course not found";
    private static final String USER_EXISTS1 = "User ";
    private static final String USER_EXISTS2 = " already exists!";
    private static final String TITLE_NULL = " Title can not be empty! ";
    private static final String DELETE_COURSE_EXCEPTION = "Can not delete course! Course has enrolled users.";
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

    public List<CourseDTO> getCoursesFromPage(Integer pageNumber, Integer numerbOfObjectPerPage) {
        return courseMapper.entitiesToDTOs(courseDAO.coursesFromPage(pageNumber, numerbOfObjectPerPage));
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
            throw new InvalidDataException(TITLE_NULL);
        }


        Course courseEntity = courseMapper.mapToEntity(course, courseDAO.findEntity(course.getIdCourse()));
        if (contactPersonsId != null && contactPersonsId.size() > 0) {
            List<User> contanctPerson = new ArrayList<>();
            contactPersonsId.forEach(id -> contanctPerson.add(userDAO.findEntity(id)));
            for (int i = 0; i < contanctPerson.size(); i++) {
                if (!courseEntity.getContactPersons().contains(contanctPerson.get(i))) {
                    courseEntity.getContactPersons().add(contanctPerson.get(i));
                } else {
                    throw new InvalidDataException(USER_EXISTS1 + contanctPerson.get(i).getEmail() + USER_EXISTS2);
                }
            }
        }


        if (ownersIds != null && ownersIds.size() > 0) {
            List<User> owners = new ArrayList<>();
            ownersIds.forEach(id -> owners.add(userDAO.findEntity(id)));
            for (int i = 0; i < owners.size(); i++) {
                if (!courseEntity.getOwners().contains(owners.get(i))) {
                    courseEntity.getOwners().add(owners.get(i));

                } else {
                    throw new InvalidDataException(USER_EXISTS1 + owners.get(i).getEmail() + USER_EXISTS2);
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
        courseDAO.persistEntity(courseEntity);
        subjectDAO.persistEntity(subjectEntity);
    }

    public CourseDTO addCourse(CourseDTO courseDTO, List<Integer> ownersIds, List<Integer> contactPersonsId) throws InvalidDataException {
        courseValidator.validateCourseData(courseDTO);
        courseValidator.validateContactPersons(contactPersonsId);
        courseValidator.validateOwners(ownersIds);

        Course course = courseMapper.mapToEntity(courseDTO, new Course());
        List<User> owners = new ArrayList<>();
        ownersIds.forEach(ownerId -> owners.add(userDAO.findEntity(ownerId)));

        List<User> constantPerson = new ArrayList<>();
        contactPersonsId.forEach(cpId -> constantPerson.add(userDAO.findEntity(cpId)));

        course.setOwners(owners);
        course.setContactPersons(constantPerson);

        return courseMapper.mapToDTO(courseDAO.persistEntity(course));
    }


    public void deleteCourse(CourseDTO course) throws CourseNotFoundException, DeleteCourseException {
        Course courseEntity = courseDAO.findEntity(course.getIdCourse());
        if (courseEntity == null)
            throw new CourseNotFoundException(COURSE_NOT_FOUND);
        if (courseEntity.getEnrolledUsers() != null && courseEntity.getEnrolledUsers().size() > 0) {
            throw new DeleteCourseException(DELETE_COURSE_EXCEPTION);
        }
        courseEntity.setContactPersons(null);
        courseEntity.setOwners(null);
        courseDAO.deleteEntity(courseEntity);
    }

    public Integer calculateProgress(UserDTO user, CourseDTO course) {
        int totalNumberOfSubjects = 0;
        Course courseEntity=courseDAO.findEntity(course.getIdCourse());
        int numberOfSubjectsCompletedByUser;
        if (courseEntity.getSubjects() != null) {
            totalNumberOfSubjects = courseEntity.getSubjects().size();
        }
        try {
            numberOfSubjectsCompletedByUser = user_subjectDAO.getSubjectsCompletedByUser(userDAO.findUserByUsername(user.getUsername()).get(),courseEntity ).size();
        } catch (NullPointerException nullPointerException) {

            numberOfSubjectsCompletedByUser = 0;
        }

        try {
            return new Integer ((int)(((double)numberOfSubjectsCompletedByUser / totalNumberOfSubjects)*100));
        } catch (IllegalArgumentException arg) {
            return 0;
        }
    }
}
