package service;

import dao.CourseDAO;
import dao.EnrollDAO;
import dao.UserDAO;
import dto.CourseDTO;
import dto.UserDTO;
import dto.mapper.CourseMapper;
import dto.mapper.UserMapper;
import entity.Course;
import entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CourseService {
    @Autowired
    private EnrollDAO enrollDAO;

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private CourseDAO courseDAO;

    private CourseMapper courseMapper= CourseMapper.INSTANCE;
    private UserMapper userMapper = UserMapper.INSTANCE;


    public void enrollUserToCourse(String username, Integer idCourse) {
        User userEntity = userDAO.findUserByUsername(username);
        Course courseEntity = courseDAO.findEntity(idCourse);
        enrollDAO.enrollUserToCourse(userEntity,courseEntity);

    }

    public void createCourse(CourseDTO courseDTO) {
        if (null == courseDTO.getContactPersons()){
            courseDTO.setContactPersons(new ArrayList<>());
        }
        if (null == courseDTO.getEnrolledUsers()){
            courseDTO.setEnrolledUsers(new ArrayList<>());
        }
        if (null == courseDTO.getMaterials()){
            courseDTO.setMaterials(new ArrayList<>());
        }
        if (null == courseDTO.getOwners()){
            courseDTO.setOwners(new ArrayList<>());
        }
        if (null == courseDTO.getSubjects()){
            courseDTO.setSubjects(new ArrayList<>());
        }
        Course courseEntity= courseMapper.mapToNewEntity(courseDTO);
        courseDAO.persistEntity(courseEntity);
    }

    public List<CourseDTO> getAllCourses() {
        return courseMapper.entitiesToDTOs(courseDAO.allCourses());
    }

    public CourseDTO getCourseById(Integer id) {
        return courseMapper.mapToDTO(courseDAO.findEntity(id));
    }

    public List<CourseDTO> searchByOverview(String overview) {
        return courseMapper.entitiesToDTOs(courseDAO.searchByOverview(overview));
    }

    public boolean userIsEnrolledOnCourse(String username, Integer idCourse) {
        User userEntity = userDAO.findUserByUsername(username);
        Course courseEntity = courseDAO.findEntity(idCourse);
        if(userEntity.getEnrolledCourses().contains(courseEntity)){
            return true;
        }
        return false;
    }

    public void unenrollUserToCourse(String username, Integer idCourse) {
        User userEntity = userDAO.findUserByUsername(username);
        Course courseEntity = courseDAO.findEntity(idCourse);
        enrollDAO.unenrollUserToCourse(userEntity,courseEntity);
    }
}
