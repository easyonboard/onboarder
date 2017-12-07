package entities;

import entities.enums.RoleType;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
public class Role implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idRole;

    @NotNull
    @Column
    @Enumerated(EnumType.STRING)
    private RoleType role;

    public int getIdRole() {
        return idRole;
    }
    public RoleType getRole() {
        return role;
    }

    public void setRole(RoleType role) {
        this.role = role;
    }
}
