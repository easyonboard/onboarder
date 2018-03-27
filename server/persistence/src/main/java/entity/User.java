package entity;


import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;

@NamedQueries({@NamedQuery(name = User.FIND_USER_BY_USERNAME, query = "select u from User u where u.username=:username"),
        @NamedQuery(name = User.FIND_USER_BY_EMAIL, query = "select u from User u where u.email=:email")})
@Entity
@Table(name = "app_user")
public class User implements Serializable {

    public static final String FIND_USER_BY_USERNAME = "User.findUSerByUsername";
    public static final String FIND_USER_BY_EMAIL = "User.findUSerByEmail";
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int idUser;

    @NotNull
    @Column
    private String name;


    @NotNull
    @Size(min = 6)
    private String username;

    @NotNull
    @Size(min = 6)
    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    @NotNull
    private String email;


    @ManyToOne
    private Role role;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "contactPersons", targetEntity = Course.class)
    private List<Course> contactForCourses;

    @ManyToMany(mappedBy = "owners", targetEntity = Course.class)
    private List<Course> ownerForCourses;

    @ManyToMany(mappedBy = "enrolledUsers", targetEntity = Course.class)
    private List<Course> enrolledCourses;

    @OneToOne(mappedBy = "buddyUser", targetEntity = UserInformation.class)
    private UserInformation buddyUser;


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

    public List<Course> getEnrolledCourses() {
        return enrolledCourses;
    }

    public void setEnrolledCourses(List<Course> enrolledCourses) {
        this.enrolledCourses = enrolledCourses;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public UserInformation getBuddyUser() {
        return buddyUser;
    }

    public void setBuddyUser(UserInformation buddyUser) {
        this.buddyUser = buddyUser;
    }
}
