package dto;

import entity.enums.RoleType;

public class RoleDTO {

    private int idRole;

    private RoleType role;

    public int getIdRole() {
        return idRole;
    }

    public void setIdRole(int idRole) {
        this.idRole = idRole;
    }

    public RoleType getRole() {
        return role;
    }

    public void setRole(RoleType role) {
        this.role = role;
    }
}
