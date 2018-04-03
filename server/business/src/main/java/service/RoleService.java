package service;

import dao.RoleDAO;
import dto.RoleDTO;
import dto.mapper.RoleMapper;
import entity.Role;
import exception.RoleNameNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleService {

    @Autowired
    private RoleDAO roleDAO;
    private RoleMapper roleMapper = RoleMapper.INSTANCE;

    public RoleDTO findRoleById(int roleId) throws RoleNameNotFoundException {

        Role entity = roleDAO.findEntity(roleId);

        return roleMapper.mapToDTO(entity);
    }
}
