package dao;

import entity.CheckList;
import entity.Course;
import entity.User;
import org.springframework.stereotype.Service;

import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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


    public List<User> searchByUsername(String name) {

        return null;

    }

    /**
     * returns map of checklist properties and values for a specific user
     */
    public Map<String, Boolean> getCheckListForUser(User user) {
        String queryString = "select cl from CheckList cl where cl.userAccount=:user";
        Query query = em.createQuery(queryString);
        query.setParameter("user", user);
        CheckList checkList = (CheckList) query.getSingleResult();
        Map checkListMap = new HashMap();
        Field fields[] = checkList.getClass().getDeclaredFields();
        boolean value = false;
        String attribute;
        for (int i = 2; i < fields.length; i++) {

            attribute = fields[i].getName();
            try {
                fields[i].setAccessible(true);
                value = (boolean) fields[i].get(checkList);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            checkListMap.put(attribute, value);
        }

        return checkListMap;
    }
}