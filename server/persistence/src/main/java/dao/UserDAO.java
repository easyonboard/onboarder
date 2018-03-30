package dao;

import entity.Course;
import entity.User;
import org.springframework.stereotype.Service;

import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

@Service
public class UserDAO extends AbstractDAO<User> {


    @Override
    public Class<User> getEntityClass() {
        return User.class;
    }

    /**
     * Identify a user by username
     *
     * @param username String
     * @return User
     */
    public Optional<User> findUserByUsername(String username) {
        TypedQuery<User> query = this.em.createNamedQuery(User.FIND_USER_BY_USERNAME, User.class);
        query.setParameter("username", username);
        Optional<User> firstUser = query.getResultList().stream().findFirst();
        return firstUser;
    }

    public User userByUsername(String username) {
        TypedQuery<User> query = this.em.createNamedQuery(User.FIND_USER_BY_USERNAME, User.class);
        query.setParameter("username", username);
        User firstUser = query.getResultList().stream().findFirst().get();
        return firstUser;
    }

    /**
     * Identify a user by email
     *
     * @param email the user email
     * @return User
     */
    public Optional<User> findUserByEmail(String email) {
        TypedQuery<User> query = this.em.createNamedQuery(User.FIND_USER_BY_EMAIL, User.class);
        query.setParameter("email", email);
        Optional<User> firstUser = query.getResultList().stream().findFirst();
        return firstUser;
    }

    public List<User> getAllUsers() {
        Query query = em.createQuery("Select u from User u ");
        return query.getResultList();
    }

    public List<Course> getcoursesForUser(String username) {
        String queryString = "select u.enrolledCourses from User u where u.username=:username";
        Query query = this.em.createQuery(queryString);
        query.setParameter("username", username);
        return query.getResultList();
    }


    public List<User> searchByUsername(String name){

        return null;

    }
}