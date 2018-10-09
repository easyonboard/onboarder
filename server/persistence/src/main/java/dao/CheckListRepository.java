package dao;

import entity.CheckList;
import entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface CheckListRepository extends JpaRepository<CheckList, Integer> {

    CheckList findByUserAccount(User userAccount);

    @Query("Select c.mailSent from CheckList c where c.userAccount=:user")
    boolean getValueForMailSent(@Param("user") User user);

    @Transactional
    @Modifying
    @Query("UPDATE CheckList c SET c.mailSent = :value  WHERE c.userAccount.idUser = :idUser")
    void updateFieldMailSentToNewEmployee(@Param("idUser") Integer idUser,  @Param("value") boolean value);

    @Transactional
    @Modifying
    @Query("UPDATE CheckList c SET c.mailSentToBuddy = :value  WHERE c.userAccount.idUser = :idUser")
    void updateFieldMailSentToBuddy(@Param("idUser") Integer idUser,  @Param("value") boolean value);

}
