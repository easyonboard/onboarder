package dao;

import entity.User;
import org.springframework.stereotype.Service;

import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;

@Service
public class UserDAO extends AbstractDAO<User> {


    @Override
    public Class<User> getEntityClass() {
        return User.class;
    }

    /**
     * Identify a user by username
     * @param  username String
     * @return User
     */
    public User findUserByUsername(String username) {
        TypedQuery<User> query = this.em.createNamedQuery(User.FIND_USER_BY_USERNAME, User.class);
        query.setParameter("username", username);
        try {

            User user = query.getSingleResult();
            return user;

        } catch (NoResultException exception) {
            return null;

        }
    }

    /**
     * Method used for searching users to add in contact or owner List for a course
     * @param email
     * @return List of String
     */
    public List<String> getUsersEmails(String email) {

        Query query = em.createQuery("Select u.email from User u where u.email LIKE :g");
        query.setParameter("g", "%" + email + "%");

        return query.getResultList();
    }

    /**
     * Identify a user by email
     * @param  email the user email
     * @return User
     */
    public User findUserByEmail(String email) {
        TypedQuery<User> query = this.em.createNamedQuery(User.FIND_USER_BY_EMAIL, User.class);
        query.setParameter("email", email);
        try {

            User user = query.getSingleResult();
            return user;

        } catch (NoResultException exception) {
            return null;

        }
    }
}