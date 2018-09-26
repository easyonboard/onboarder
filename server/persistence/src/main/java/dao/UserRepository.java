package dao;

import entity.User;
import entity.enums.DepartmentType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByUsername(String username);

    Optional<User> findByMsgMail(String msgMail);

    List<User> findByNameContainingIgnoreCase(String name);

    @Query("select ui.userAccount from UserInformation ui where ui.department=:department")
    List<User> findByDepartment(@Param("department") DepartmentType department);

    @Query("select ui.department from UserInformation ui where ui.userAccount.username=:username")
    String findDepartmentByUsername(@Param("username") String username);


    /**
     *  query for retrieving users that start after a given date
     * @param date: given date
     * @return list of users
     */
    List<User> findByStartDateAfter(Date date);

    /**
     * query for retrieving users that start at a given date
     * @param date: given Date
     * @return list of users
     */
    List<User> findByStartDate(Date date);

    /**
     *
     * @param buddyUser: User
     * @return list of user information with user param assigned as buddy
     */
    List<User> findByBuddyUser(User buddyUser);


}
