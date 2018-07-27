package dao;

import entity.User;
import entity.UserInformation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.NoResultException;
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
    UserRepository userRepository;

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
        User userAccount2 = userRepository.findByUsername(userAccount.getUsername()).get();

        Query q = em.createQuery("select o from UserInformation o where o.userAccount =:userAccount");
        q.setParameter("userAccount", userAccount2);

        return (UserInformation) q.getSingleResult();
    }

    @Transactional
    public UserInformation updateUserInformation(UserInformation userInfo) {
        UserInformation actualUserInfo = findEntity(userInfo.getIdUserInformation());

        actualUserInfo.setTeam(userInfo.getTeam());
        actualUserInfo.setLocation(userInfo.getLocation());
        actualUserInfo.setFloor(userInfo.getFloor());
        actualUserInfo.setProject(userInfo.getProject());

        actualUserInfo.setDepartment(userInfo.getDepartment());
        actualUserInfo.setStartDate(userInfo.getStartDate());
        if (userInfo.getBuddyUser().getUsername() != null) {
            User newUser = userRepository.findByUsername(userInfo.getBuddyUser().getUsername()).get();
            actualUserInfo.setBuddyUser(newUser);
        }

        return em.merge(actualUserInfo);
    }

    public List<UserInformation> usersWhoStartOnGivenDate(Date givenDate) {
        Query q = em.createQuery("select o from UserInformation o where o.startDate = :givenDate ");
        q.setParameter("givenDate", givenDate, TemporalType.DATE);

        return q.getResultList();
    }

    public UserInformation findUserInformationByUser(User userEntity) {
        Query q = em.createQuery("select us from UserInformation us where us.userAccount=:userEntity");
        q.setParameter("userEntity", userEntity);
        try {
            return (UserInformation) q.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Transactional
    public void setBuddyToNull(User buddyUser) {
        Query q = em.createQuery("select us from UserInformation us where us.buddyUser=:buddyUser");
        q.setParameter("buddyUser", buddyUser);
        try {
            List<UserInformation> userInformationsList = (List<UserInformation>) q.getResultList();
            if (userInformationsList != null) {
                for (int i = 0; i < userInformationsList.size(); i++) {
                    UserInformation userInformation = userInformationsList.get(i);
                    userInformation.setBuddyUser(null);
                    persistEntity(userInformation);
                }
            }
        } catch (NoResultException e) {

            System.out.println("User is not buddy");
        }
    }

}
