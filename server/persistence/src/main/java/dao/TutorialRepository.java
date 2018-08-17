package dao;

import entity.Tutorial;
import entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TutorialRepository extends JpaRepository<Tutorial, Integer> {

    List<Tutorial> findByIsDraft(Boolean isDraft);

    List<Tutorial> findByKeywordsContainingIgnoreCase(@Param("keyword") String keyword);

    @Query("select t from Tutorial t where t.isDraft=true and :user member of t.contactPersons ")
    List<Tutorial> getAllDraftTurorialsForUser(@Param("user") User user);

    @Query("select t from Tutorial t where :user member of t.contactPersons ")
    List<Tutorial> getTutorialsForUser(@Param("user") User user);

}
