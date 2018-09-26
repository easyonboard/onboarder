package dto;

import entity.enums.RoleType;
import lombok.Data;

@Data
public class RoleDto {

    private int idRole;
    private RoleType role;
}
