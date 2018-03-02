package dao;

import entity.Course;
import entity.User;
import entity.User_Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EnrollDAO {

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private CourseDAO courseDAO;

    @Autowired
    private SubjectDAO subjectDAO;

    @Autowired
    private User_SubjectDAO user_subjectDAO;
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
        User_Subject user_subject=new User_Subject();
        user_subject.setUser(user);
        user_subject.setCourse(course);
        user_subject.setSubject(subjectDAO.getFirstSubjectFromCourse(course));
        user_subject.setStatus(false);
        user_subjectDAO.persistEntity(user_subject);

    }

    /**
     *
     * @param user the user who will be unenrolled
     * @param course the course to which the user will be unenrolled
     */
    public void unenrollUserToCourse(User user, Course course) {
        List<Course> enrolledCourses = user.getEnrolledCourses();
        user_subjectDAO.deleteEntriesWhenUnenroll(user, course);
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
