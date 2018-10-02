package dto;

import entity.Department;
import entity.Location;
import entity.enums.RoleType;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class UserDto {

    private Integer idUser;
    private String name;
    private String username;
    private String password;
    private String email;
    private RoleType role;
    private String msgMail;
    private Date startDate;
    private Department department;
    private UserDto buddyUser;
    private String team;
    private Location location;
    private String floor;
    private String project;
}
