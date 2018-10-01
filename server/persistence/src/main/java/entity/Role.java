package entity;

import entity.enums.RoleType;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

@Getter
@Setter
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

}
