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

    /**
     *
     * @param user the user who will be enrolled
     * @param course the course to which the user will be enrolled
     */
    public void enrollUserToCourse(User user, Course course) {
        List<Course> enrolledCourses = user.getEnrolledCourses();
        if (!enrolledCourses.contains(course)) {
            enrolledCourses.add(course);
            user.setEnrolledCourses(enrolledCourses);
            userDAO.persistEntity(user);
        }
        List<User> enrolledUsers = course.getEnrolledUsers();
        if (!enrolledUsers.contains(user)) {
            enrolledUsers.add(user);
            course.setEnrolledUsers(enrolledUsers);

            courseDAO.persistEntity(course);
        }
    }

    /**
     *
     * @param user the user who will be unenrolled
     * @param course the course to which the user will be unenrolled
     */
    public void unenrollUserToCourse(User user, Course course) {
        List<Course> enrolledCourses = user.getEnrolledCourses();
        if (enrolledCourses.contains(course)) {
            enrolledCourses.remove(enrolledCourses.indexOf(course));
            user.setEnrolledCourses(enrolledCourses);
            userDAO.persistEntity(user);
        }
        List<User> enrolledUsers = course.getEnrolledUsers();
        if (enrolledUsers.contains(user)) {
            enrolledUsers.remove(enrolledUsers.indexOf(user));
            course.setEnrolledUsers(enrolledUsers);

            courseDAO.persistEntity(course);
        }
    }
}
