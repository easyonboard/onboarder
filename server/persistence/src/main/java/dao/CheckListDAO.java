package dao;

import entity.CheckList;
import entity.User;
import org.springframework.stereotype.Service;

import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.lang.reflect.Field;

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

    @Transactional
    public void setValue(User user, String fieldName, boolean value) {
        try {
            CheckList checkListForUser = this.findByUser(user);
            Field field = null;
            field = CheckList.class.getDeclaredField(fieldName);

            field.setAccessible(true);
            field.set(checkListForUser, value);

            this.persistEntity(checkListForUser);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public boolean getValueForMailSent(User user) {
        Query query = em.createQuery("Select c.mailSent from CheckList c where c.userAccount=:user");
        query.setParameter("user", user);
        try {
            return (boolean) query.getSingleResult();
        } catch (NoResultException e) {
            return false;
        }
    }
}
