package dao;

import entity.User;
import entity.UserInformation;
import org.springframework.stereotype.Service;

import javax.persistence.Query;
import java.util.Date;
import java.util.List;

@Service
public class UserInformationDAO extends AbstractDAO<UserInformation> {


    @Override
    public Class<UserInformation> getEntityClass() {
        return UserInformation.class;
    }

    /**
     * @return list of all users whose startingDate is greater than today
     */
    public List<UserInformation> getAllNewUsers() {
        Query q = em.createQuery("select o from UserInformation o where o.startDate > :today ");
        Date date = new Date();
        q.setParameter("today", date);
        return q.getResultList();
    }

    public UserInformation getUserInformationForUserAccount(User userAccount) {
        Query q = em.createQuery("select o from UserInformation o where o.userAccount =:userAccount");
        q.setParameter("userAccount", userAccount);
        return (UserInformation) q.getSingleResult();
    }

    public UserInformation updateEntity(UserInformation entity) {
        return em.merge(entity);
    }

}
