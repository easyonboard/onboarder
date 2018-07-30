package dao;

import entity.Tutorial;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TutorialRepository extends JpaRepository<Tutorial, Integer> {

    List<Tutorial> findByIsDraft(Boolean isDraft);
    List<Tutorial> findByKeywordsContainingIgnoreCase(String keyword);
    List<Tutorial> findByIsDraft

}
