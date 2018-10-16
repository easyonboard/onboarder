package dto;

import entity.Department;
import entity.Location;
import entity.User;
import entity.enums.RoleType;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

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
    private String mateUsername;
    private String team;
    private Location location;
    private String floor;
    private String project;
}
