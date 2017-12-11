package dao;
import entity.User;
import org.springframework.stereotype.Service;

import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import javax.persistence.TypedQuery;

@Service
public class UserDAO extends AbstractDAO<User>{


    @Override
    public Class<User> getEntityClass() {
        return User.class;
    }

    public User findEntityByUsername(String username) {
        CriteriaBuilder cb=  this.getCriteriaBuilder();
        CriteriaQuery<User> criteriaQuery = cb.createQuery(User.class);
        Root<User> rootUser = criteriaQuery.from(User.class);

        criteriaQuery.select(rootUser).where(cb.equal(rootUser.get("username"),username));
        return (User) this.executeCriteriaQuery(criteriaQuery).get(0);
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
}
