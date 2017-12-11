package dao;

import entity.Role;
import org.springframework.stereotype.Service;

@Service
public class RoleDAO extends AbstractDAO<Role> {
    @Override
    public Class<Role> getEntityClass() {
        return Role.class;
    }
}
