package dao;

import entity.Permission;
import org.springframework.stereotype.Service;

@Service
public class PermissionDAO extends AbstractDAO<Permission> {
    @Override
    public Class<Permission> getEntityClass() {
        return Permission.class;
    }
}
