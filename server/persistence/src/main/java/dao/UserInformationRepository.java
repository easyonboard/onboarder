package dao;

import entity.User;
import entity.UserInformation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface UserInformationRepository extends JpaRepository<UserInformation, Integer> {

    /**
     *  query for retrieving users that start after a given date
     * @param date: given date
     * @return list of users
     */
    List<UserInformation> findByStartDateAfter(Date date);

    /**
     * query for retrieving users that start at a given date
     * @param date: given Date
     * @return list of users
     */
    List<UserInformation> findByStartDateBefore(Date date);

    /**
     *
     * @param userEntity: User
     * @return user information of user param
     */
    UserInformation findByUserAccount(User userEntity);

    /**
     *
     * @param buddyUser: User
     * @return list of user information with user param assigned as buddy
     */
    List<UserInformation> findByBuddyUser(User buddyUser);

}
