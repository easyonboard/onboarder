package service;

import dao.RoleDAO;
import dto.RoleDto;
import dto.mapper.RoleMapper;
import entity.Role;
import entity.enums.RoleType;
import exception.types.FieldNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RoleService {

    @Autowired
    private RoleDAO roleDAO;
    private RoleMapper roleMapper = RoleMapper.INSTANCE;

    public RoleDto findRoleByType(RoleType roleType) throws FieldNotFoundException {
        if (roleType != null) {
            Optional<Role> searchedRole = roleDAO.getAllRoles().stream().filter(r -> r.getRole().name().equals(roleType.name())).findFirst();
            if (searchedRole.isPresent()) {
                return roleMapper.mapToDTO(searchedRole.get());
            } else{
                Role role1= new Role(roleType);
                roleDAO.persistEntity(role1);
                return this.findRoleByType(roleType);
            }
        } else {
            throw new FieldNotFoundException("The role of the new user must be provided");
        }

    }
}
