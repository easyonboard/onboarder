package dao;

import entity.CheckList;
import entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CheckListRepository extends JpaRepository<CheckList, Integer> {

    CheckList findByUserAccount(User userAccount);

    @Query("Select c.mailSent from CheckList c where c.userAccount=:user")
    boolean getValueForMailSent(@Param("user") User user);

}
