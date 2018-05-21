package dao;

import entity.Course;
import entity.Tutorial;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Optional;

@Repository
public class TutorialDAO extends AbstractDAO<Tutorial> {

    @Override
    public Class<Tutorial> getEntityClass() {
        return Tutorial.class;
    }

    public List<Tutorial> allTutorials() {
//        CriteriaBuilder cb = this.getCriteriaBuilder();
//        CriteriaQuery<Tutorial> criteriaQuery = cb.createQuery(Tutorial.class);
//        Root<Tutorial> rootTutorial = criteriaQuery.from(Tutorial.class);
//        criteriaQuery.select(cb.construct(Tutorial.class));
//        return (List<Tutorial>) this.executeCriteriaQuery(criteriaQuery);
        Query query = em.createQuery("SELECT m FROM Tutorial m");

        return query.getResultList();
    }

    public List<Tutorial> filterByKeyword(String keyword) {
        CriteriaBuilder cb = this.getCriteriaBuilder();
        CriteriaQuery<Tutorial> criteriaQuery = cb.createQuery(Tutorial.class);
        Root<Tutorial> rootTutorial = criteriaQuery.from(Tutorial.class);
        criteriaQuery.select(cb.construct(Tutorial.class,
                rootTutorial.get("idTutorial"), rootTutorial.get("titleTutorial"), rootTutorial.get("overview"), rootTutorial.get("keywords"))).where(cb.like(cb.upper(rootTutorial.get("keywords")), "%" + keyword.toUpperCase() + "%"));
        List<Tutorial> tutorials = this.executeCriteriaQuery(criteriaQuery);
        return tutorials;
    }

    public Tutorial findTutorialById(Integer tutorialId) {
        TypedQuery<Tutorial> query = this.em.createNamedQuery(Tutorial.FIND_TUTORIAL_BY_ID, Tutorial.class);
        query.setParameter("idTutorial", tutorialId);
        Tutorial tutorial = query.getResultList().stream().findFirst().get();
        return tutorial;
    }

}
