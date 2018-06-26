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

    public static final String FIND_USER_BY_USERNAME = "User.findUserByUsername";
    public static final String FIND_USER_BY_EMAIL = "User.findUserByEmail";

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
    @Column
    private String msgMail;

    @ManyToOne
    private Role role;

    @OneToMany(mappedBy = "contactPerson", cascade = CascadeType.ALL)
    private List<Event> events;

    public User(Integer idUser,@NotNull String name, @NotNull @Size(min = 6) String username, @NotNull String email) {
        this.name = name;
        this.username = username;
        this.email = email;
    }

    public User() {
    }

    @OneToOne( mappedBy = "userAccount", targetEntity = UserInformation.class)
    public UserInformation userAccount;



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

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }



    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }



    public UserInformation getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(UserInformation userAccount) {
        this.userAccount = userAccount;
    }

    public String getMsgMail() {
        return msgMail;
    }

    public void setMsgMail(String msgMail) {
        this.msgMail = msgMail;
    }
}
