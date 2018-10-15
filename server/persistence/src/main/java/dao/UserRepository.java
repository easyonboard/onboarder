package dao;

import entity.Department;
import entity.User;
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

    List<User> findByDepartment(Department department);

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
    List<User> findByStartDateBefore(Date date);
}
