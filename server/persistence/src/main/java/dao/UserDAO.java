package dao;

import entity.CheckList;
import entity.Course;
import entity.User;
import entity.enums.DepartmentType;
import org.springframework.stereotype.Service;

import javax.persistence.NoResultException;
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


    public List<User> searchByName(String name) {

        String queryString = "select u from User u where u.name LIKE :name";
        Query query = this.em.createQuery(queryString);
        query.setParameter("name", "%" + name + "%");
        return query.getResultList();

    }

    public List<User> getUsersInDepartment(String department) {

        String queryString = "select ui.userAccount from UserInformation ui where ui.department=:department";
        Query query = this.em.createQuery(queryString);
        query.setParameter("department", DepartmentType.valueOf(department));
        return query.getResultList();
    }

    /**
     * returns map of checklist properties and values for a particular user
     */
    public Map<String, Boolean> getCheckListMapForUser(User user) {
        String queryString = "select cl from CheckList cl where cl.userAccount=:user";
        Query query = em.createQuery(queryString);
        query.setParameter("user", user);
        try {
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
        } catch (NoResultException e) {

            return null;

        }
    }


    public String getDepartmentForLoggedUser(String username) {

        Optional<User> userOptional = findUserByUsername(username);
        User user;
        if (userOptional.isPresent()) {
            user = userOptional.get();
            String queryString = "select ui.department from UserInformation ui where ui.userAccount=:user";
            Query query = this.em.createQuery(queryString);
            query.setParameter("userAccount", (user));
            DepartmentType departmentType = (DepartmentType) query.getSingleResult();
            return departmentType.toString();
        }
        return null;
    }
}