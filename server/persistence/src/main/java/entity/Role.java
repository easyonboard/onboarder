package entity;

import entity.enums.RoleType;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

@Entity
@NamedQueries({@NamedQuery(name = Role.FIND_ROLE_BY_ID, query = "select r from Role r where r.idRole=:idRole")})
public class Role implements Serializable {

    public static final String FIND_ROLE_BY_ID = "Role.findRoleById";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int idRole;

    @NotNull
    @Column
    @Enumerated(EnumType.STRING)
    private RoleType role;

    public Role() {
    }

    public Role(@NotNull RoleType role) {
        this.role = role;
    }

    public int getIdRole() {
        return idRole;
    }

    public RoleType getRole() {
        return role;
    }

    public void setRole(RoleType role) {
        this.role = role;
    }

    public void setIdRole(int idRole) {
        this.idRole = idRole;
    }

}
