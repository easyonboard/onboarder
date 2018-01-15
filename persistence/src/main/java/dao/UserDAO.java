package dao;

import entity.User;
import org.springframework.stereotype.Service;

import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
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


    public List<String> getUsersEmails(){

        Query query=em.createQuery("Select email from User u");
        return query.getResultList();
    }
}