package service;

import dao.RoleDAO;
import dto.RoleDto;
import dto.mapper.RoleMapper;
import entity.Role;
import entity.enums.RoleType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RoleService {

    @Autowired
    private RoleDAO roleDAO;
    private RoleMapper roleMapper = RoleMapper.INSTANCE;

    public RoleDto findRoleByType(RoleType roleType) {
        Optional<Role> searchedRole = roleDAO.getAllRoles().stream().filter(r -> r.getRole().name().equals(roleType.name())).findFirst();
        if (searchedRole.isPresent()) {
            return roleMapper.mapToDTO(searchedRole.get());
        } else{
            Role role1= new Role(roleType);
            roleDAO.persistEntity(role1);
            return this.findRoleByType(roleType);
        }
    }
}
