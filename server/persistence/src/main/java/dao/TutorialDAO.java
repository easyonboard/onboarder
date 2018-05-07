package dao;

import entity.Course;
import entity.Tutorial;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@Repository
public class TutorialDAO extends AbstractDAO<Tutorial> {

    @Override
    public Class<Tutorial> getEntityClass() {
        return Tutorial.class;
    }

    public List<Tutorial> allCourses() {
        CriteriaBuilder cb = this.getCriteriaBuilder();
        CriteriaQuery<Tutorial> criteriaQuery = cb.createQuery(Tutorial.class);
        Root<Tutorial> rootTutorial = criteriaQuery.from(Tutorial.class);
        criteriaQuery.select(cb.construct(Tutorial.class, rootTutorial.get("idTutorial"), rootTutorial.get("titleTutorial"),
                rootTutorial.get("overview"), rootTutorial.get("keywords")));
        return (List<Tutorial>) this.executeCriteriaQuery(criteriaQuery);
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

}
