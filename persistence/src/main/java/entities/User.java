package entities;


import entity.Course;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "app_user")
public class User implements Serializable {

//    TODO: before: GenerationType.IDENTITY  => now: GenerationType.SEQUENCE.
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int idUser;

    @NotNull
    @Column
    private String name;


    @NotNull
    @Min(6)
    private String username;

    @NotNull
    @Column(nullable = false)
    @Min(6)
    private String password;

    @ManyToOne
    private Role role;

    @ManyToMany(mappedBy = "contactPersons", targetEntity = Course.class)
    private List<Course> contactForCourses;

    @ManyToMany(mappedBy = "owners", targetEntity = Course.class)
    private List<Course> ownerForCourses;

    public int getIdUser() {
        return idUser;
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

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public List<Course> getContactForCourses() {
        return contactForCourses;
    }

    public void setContactForCourses(List<Course> contactForCourses) {
        this.contactForCourses = contactForCourses;
    }

    public List<Course> getOwnerForCourses() {
        return ownerForCourses;
    }

    public void setOwnerForCourses(List<Course> ownerForCourses) {
        this.ownerForCourses = ownerForCourses;
    }
}
