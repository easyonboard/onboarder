package dao;

import entity.Tutorial;
import entity.User;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.util.List;


@Repository
public class TutorialDAO extends AbstractDAO<Tutorial> {

    @Override
    public Class<Tutorial> getEntityClass() {
        return Tutorial.class;
    }

    public List<Tutorial> allPublicTutorials() {
        Query query = em.createQuery("SELECT t FROM Tutorial t where t.isDraft=false");

        return query.getResultList();
    }

    public List<Tutorial> filterByKeyword(String keyword) {
        CriteriaBuilder cb = this.getCriteriaBuilder();
        CriteriaQuery<Tutorial> criteriaQuery = cb.createQuery(Tutorial.class);
        Root<Tutorial> rootTutorial = criteriaQuery.from(Tutorial.class);
        criteriaQuery.select(rootTutorial)
                .where(cb.and(cb.like(cb.upper(rootTutorial.get("keywords")), "%" + keyword.toUpperCase() + "%")),
                        (cb.equal(rootTutorial.get("isDraft"), false)));
        List<Tutorial> tutorials = this.executeCriteriaQuery(criteriaQuery);
        return tutorials;
    }

    public Tutorial findTutorialById(Integer tutorialId) {
        TypedQuery<Tutorial> query = this.em.createNamedQuery(Tutorial.FIND_TUTORIAL_BY_ID, Tutorial.class);
        query.setParameter("idTutorial", tutorialId);
        Tutorial tutorial = query.getResultList().stream().findFirst().get();
        return tutorial;
    }

    @Transactional
    public void removeUserFromTutorialContactList(User user) {
        Query query = em.createQuery("select t from Tutorial t where :user member of t.contactPersons ");
        query.setParameter("user", user);
        try {
            List<Tutorial> tutorialsList = query.getResultList();
            for (int i = 0; i < tutorialsList.size(); i++) {
                tutorialsList.get(i).getContactPersons().remove(user);
                persistEntity(tutorialsList.get(i));
            }

        } catch (NoResultException e) {
        }
    }

    public List<Tutorial> getAllDraftTutorialsForUser(User user) {
        Query query = em.createQuery("SELECT t FROM Tutorial t where t.isDraft=true and :user member of t.contactPersons");
        query.setParameter("user", user);
        return query.getResultList();

    }
//    public List<Tutorial> getTutorialsWhereUserIsContactPerson(User user) {
//        Query query = em.createQuery(
//                "select distinct t " +
//                        "from Tutorial t " +
//                        "where :user member of t.contactPersons ");
//        query.setParameter("user", user);
//        try {
//            List<Tutorial> tutorialsList = query.getResultList();
//            for (int i = 0; i < tutorialsList.size(); i++) {
//                tutorialsList.get(i).getContactPersons().remove(user);
//                persistEntity(tutorialsList.get(i));
//            }
//
//        } catch (NoResultException e) {
//        }
//
//    }

}
