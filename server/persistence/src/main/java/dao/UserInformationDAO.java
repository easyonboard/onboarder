package dao;

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
     *
     * @return list of all users whose startingDate is greater than today
     */
    public List<UserInformation> getAllNewUsers() {
        Query q = em.createQuery("select o from UserInformation o where o.startDate > :today ");
        Date date = new Date();
        q.setParameter("today", date);
        return q.getResultList();
    }

}
