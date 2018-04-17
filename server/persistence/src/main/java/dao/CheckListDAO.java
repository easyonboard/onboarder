package dao;

import entity.CheckList;
import entity.User;
import org.springframework.stereotype.Service;

import javax.persistence.NoResultException;
import javax.persistence.Query;

@Service
public class CheckListDAO extends AbstractDAO<CheckList> {
    @Override
    public Class<CheckList> getEntityClass() {
        return CheckList.class;
    }


    public CheckList findByUser(User userEntity) {

        Query query = em.createQuery("Select c from CheckList c where c.userAccount=:user");
        query.setParameter("user", userEntity);
        try {
            return (CheckList) query.getSingleResult();

        } catch (NoResultException e) {
            return null;
        }
    }


}
