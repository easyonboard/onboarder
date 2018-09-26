package dto;

import entity.Department;
import entity.Location;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Data
public class UserDto {

    @Getter
    private Integer idUser;

    @Getter
    @Setter
    private String name;
    @Getter
    @Setter
    private String username;
    @Getter
    @Setter
    private String password;
    @Getter
    @Setter
    private String email;
    @Getter
    @Setter
    private RoleDto role;
    @Getter
    @Setter
    private String msgMail;
    @Getter
    @Setter
    private Date startDate;
    @Getter
    @Setter
    private Department department;
    @Getter
    @Setter
    private UserDto buddyUser;
    @Getter
    @Setter
    private String team;
    @Getter
    @Setter
    private Location location;
    @Getter
    @Setter
    private String floor;
    @Getter
    @Setter
    private String project;
}
