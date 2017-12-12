package dao;

import entity.Course;
import entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EnrollDAO {

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private CourseDAO courseDAO;

    public void enrollUserToCourse(User user, Course course) {
        List<Course> enrolledCourses= user.getEnrolledCourses();
        if (!enrolledCourses.contains(course)) {
            enrolledCourses.add(course);
            user.setEnrolledCourses(enrolledCourses);
            userDAO.persistEntity(user);
        }
        List<User> enrolledUsers = course.getEnrolledUsers();
        if(!enrolledUsers.contains(user)) {
            enrolledUsers.add(user);
            course.setEnrolledUsers(enrolledUsers);

            courseDAO.persistEntity(course);
        }
    }
}
