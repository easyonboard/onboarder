package entity;

import entity.enums.PermissionType;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
public class Permission  implements Serializable{

    //    TODO: before: GenerationType.IDENTITY  => now: GenerationType.SEQUENCE.
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int idPermission;

    @NotNull
    @Column
    @Enumerated(EnumType.STRING)
    private PermissionType permissionType;


    public int getIdPermission() {
        return idPermission;
    }
    public PermissionType getPermissionType() {
        return permissionType;
    }

    public void setPermissionType(PermissionType permissionType) {
        this.permissionType = permissionType;
    }
}
