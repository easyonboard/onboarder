package dao;

import entity.Course;
import entity.User;
import org.springframework.stereotype.Service;

import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@Service
public class UserDAO extends AbstractDAO<User>{


    @Override
    public Class<User> getEntityClass() {
        return User.class;
    }

    public User findUserByUsername(String username){
        TypedQuery<User> query=this.em.createNamedQuery(User.FIND_USER_BY_USERNAME, User.class);
        query.setParameter("username", username);
    try{

        User user=query.getSingleResult();
        return user;

    }catch (NoResultException exception){
        return null;

    }
    }


    public List<String> getUsersEmails(String email){

        Query query=em.createQuery("Select u.email from User u where u.email LIKE :g");
        query.setParameter("g","%"+email+"%");

        return query.getResultList();
    }


    public  User findUserByEmail(String email){
        TypedQuery<User> query=this.em.createNamedQuery(User.FIND_USER_BY_EMAIL, User.class);
        query.setParameter("email", email);
        try{

            User user=query.getSingleResult();
            return user;

        }catch (NoResultException exception){
            return null;

        }

    }
}