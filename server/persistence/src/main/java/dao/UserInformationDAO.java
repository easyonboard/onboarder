package dao;

import entity.User;
import entity.UserInformation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.List;

@Service
public class UserInformationDAO extends AbstractDAO<UserInformation> {

    @Override
    public Class<UserInformation> getEntityClass() {
        return UserInformation.class;
    }

    @Autowired
    UserRepository userRepository;

//    @Transactional
//    public UserInformation updateUserInformation(UserInformation userInfo) {
//        UserInformation actualUserInfo = findEntity(userInfo.getIdUserInformation());
//
//        actualUserInfo.setTeam(userInfo.getTeam());
//        actualUserInfo.setLocation(userInfo.getLocation());
//        actualUserInfo.setFloor(userInfo.getFloor());
//        actualUserInfo.setProject(userInfo.getProject());
//
//        actualUserInfo.setDepartment(userInfo.getDepartment());
//        actualUserInfo.setStartDate(userInfo.getStartDate());
//        if (userInfo.getBuddyUser().getUsername() != null) {
//            User newUser = userRepository.findByUsername(userInfo.getBuddyUser().getUsername()).get();
//            actualUserInfo.setBuddyUser(newUser);
//        }
//
//        return em.merge(actualUserInfo);
//    }

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
