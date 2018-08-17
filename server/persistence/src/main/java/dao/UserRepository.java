package dao;

import entity.CheckList;
import entity.User;
import entity.enums.DepartmentType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByUsername(String username);

    Optional<User> findByMsgMail(String msgMail);

    List<User> findByNameContainingIgnoreCase(String name);

    @Query("select ui.userAccount from UserInformation ui where ui.department=:department")
    List<User> findAllByDepartment(@Param("department") DepartmentType department);

    @Query("select ui.department from UserInformation ui where ui.userAccount.username=:username")
    String findDepartmentByUsername(@Param("username") String username);

}
