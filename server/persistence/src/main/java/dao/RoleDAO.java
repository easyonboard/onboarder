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

    public Optional<Role> findRoleById(int roleId) {
        TypedQuery<Role> query = this.em.createNamedQuery(Role.FIND_ROLE_BY_ID, Role.class);
        query.setParameter("idRole", roleId);
        Optional<Role> role = query.getResultList().stream().findFirst();
        return role;
    }

    public List<Role> getAllRoles() {
        String queryString = "select r from Role r";
        Query query = this.em.createQuery(queryString);
        return (List<Role>) query.getResultList();
    }

//    public List<Role> findRoleByType(RoleType role) {
////        String queryString = "select r from Role r where r.role=:roleName";
////        Query query = this.em.createQuery(queryString);
////        query.setParameter("roleName", role.name());
////        return (List<Role>)query.getResultList();
//        CriteriaBuilder cb = this.getCriteriaBuilder();
//        CriteriaQuery<Role> criteriaQuery = cb.createQuery(Role.class);
//        Root<Role> rootRole = criteriaQuery.from(Role.class);
//        criteriaQuery.select(cb.construct(Role.class, rootRole.get("idRole"), rootRole.get("role")))
//                .where(cb.equal(cb.upper(rootRole.get("role")), role.getRoleTypeId().));
//        List<Role> roles = this.executeCriteriaQuery(criteriaQuery);
//        return roles;
//
//    }
}
