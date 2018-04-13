package dao;

import entity.User;
import entity.UserInformation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.Query;
import javax.persistence.TemporalType;
import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class UserInformationDAO extends AbstractDAO<UserInformation> {

    @Override
    public Class<UserInformation> getEntityClass() {
        return UserInformation.class;
    }

    @Autowired
    UserDAO userDAO;

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
        User userAccount2 = userDAO.userByUsername(userAccount.getUsername());

        Query q = em.createQuery("select o from UserInformation o where o.userAccount =:userAccount");
        q.setParameter("userAccount", userAccount2);

        return (UserInformation) q.getSingleResult();
    }

    @Transactional
    public UserInformation updateUserInformation(UserInformation userInfo) {
        UserInformation actualUserInfo = findEntity(userInfo.getIdUserInformation());

        actualUserInfo.setTeam(userInfo.getTeam());
        actualUserInfo.setBuilding(userInfo.getBuilding());
        actualUserInfo.setFloor(userInfo.getFloor());

        Optional<User> newUser = userDAO.findUserByUsername(userInfo.getBuddyUser().getUsername());

        actualUserInfo.setBuddyUser(newUser.get());

        return em.merge(actualUserInfo);

    }

  public List<UserInformation> usersWhoStartOnGivenDate(Date givenDate) {
        Query q = em.createQuery("select o from UserInformation o where o.startDate = :givenDate ");
        q.setParameter("givenDate", givenDate, TemporalType.DATE);

        return q.getResultList();
    }

    @Transactional
    public void setEmailSendFlag(UserInformation ui, boolean isMailSend) {
        ui.setMailSent(isMailSend);
        this.persistEntity(ui);
    }
}
