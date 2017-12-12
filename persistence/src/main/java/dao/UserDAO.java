package dao;
import entity.User;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

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
}
