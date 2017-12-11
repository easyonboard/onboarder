package dto;

import java.util.List;

public class UserDTO {
    private Integer idUser;

    private String name;


    private String username;

    private String password;

    private RoleDTO role;

    private List<CourseDTO> contactForCourses;

    private List<CourseDTO> ownerForCourses;

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public RoleDTO getRole() {
        return role;
    }

    public void setRole(RoleDTO role) {
        this.role = role;
    }

    public List<CourseDTO> getContactForCourses() {
        return contactForCourses;
    }

    public void setContactForCourses(List<CourseDTO> contactForCourses) {
        this.contactForCourses = contactForCourses;
    }

    public List<CourseDTO> getOwnerForCourses() {
        return ownerForCourses;
    }

    public void setOwnerForCourses(List<CourseDTO> ownerForCourses) {
        this.ownerForCourses = ownerForCourses;
    }
}
