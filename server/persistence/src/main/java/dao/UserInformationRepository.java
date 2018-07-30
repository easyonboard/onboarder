package dao;

import entity.User;
import entity.UserInformation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface UserInformationRepository extends JpaRepository<UserInformation, Integer> {


    @Query("select o from UserInformation o where o.startDate > :today ")
    List<UserInformation> getAllNewUsers(@Param("today") Date date);

    @Query("select o from UserInformation o where o.startDate = :givenDate ")
    List<UserInformation> usersWhoStartOnGivenDate(@Param("givenDate") Date date);

    @Query("select us from UserInformation us where us.userAccount=:userEntity ")
    UserInformation findUserInformationByUser(@Param("userEntity") User userEntity);

    @Query("select us from UserInformation us where us.buddyUser=:buddyUser")
    List<UserInformation> findUsersByBuddyUser(@Param("buddyUser") User buddyUser);

}
