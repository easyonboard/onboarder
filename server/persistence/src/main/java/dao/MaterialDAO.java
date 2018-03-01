package dao;

import entity.Material;
import entity.Subject;
import org.springframework.stereotype.Service;

import javax.persistence.Query;
import javax.persistence.criteria.*;
import java.util.List;

@Service
public class MaterialDAO extends AbstractDAO<Material> {
    @Override
    public Class<Material> getEntityClass() {
        return Material.class;
    }

    public List<Material> getMaterialsBySubject(Subject subject) {
//        CriteriaBuilder cb = this.getCriteriaBuilder();
//        CriteriaQuery<Material> criteriaQuery = cb.createQuery(Material.class);
//        Root<Material> materialRoot = criteriaQuery.from(Material.class);
//        criteriaQuery.where(materialRoot.get("containedBySubjects"));
//
//        Expression<Material> exp = scheduleRequest.get("createdBy");
//        Predicate predicate = exp.in(myList);
//        criteria.where(predicate);
//
//        List<Material> courses = this.executeCriteriaQuery(criteriaQuery);
//        return courses;
        Query query = em.createQuery("SELECT m FROM Material  m WHERE m.subject=:sub");

        query.setParameter("sub", subject);

        return query.getResultList();
    }
}
