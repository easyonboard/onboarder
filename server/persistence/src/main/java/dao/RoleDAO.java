package dao;

import entity.Role;
import org.springframework.stereotype.Service;

import javax.persistence.TypedQuery;
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
}
