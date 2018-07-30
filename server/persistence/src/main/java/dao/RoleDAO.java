package dao;

import entity.Role;
import entity.enums.RoleType;
import org.springframework.stereotype.Service;

import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Optional;

@Service
public class RoleDAO extends AbstractDAO<Role> {

    @Override
    public Class<Role> getEntityClass() {
        return Role.class;
    }

    public List<Role> getAllRoles() {
        String queryString = "select r from Role r";
        Query query = this.em.createQuery(queryString);
        return (List<Role>) query.getResultList();
    }
}
