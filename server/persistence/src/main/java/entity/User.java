package entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import entity.enums.DepartmentType;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "app_user")
public class User implements Serializable {

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
    @Column
    private Date startDate;
    @Column
    private Department department;
    @ManyToOne
    @JoinColumn(name = "user_buddy_id")
    private User buddyUser;
    private String team;
    @ManyToOne
    private Location location;
    @Column
    private String floor;
    @Column
    private String project;

    @ManyToOne
    private Role role;


    @JsonBackReference(value = "user-event-contact-person")
    @OneToMany(mappedBy = "contactPerson", cascade = CascadeType.ALL)
    private List<Event> events;

    public User(Integer idUser, @NotNull String name, @NotNull @Size(min = 6) String username, @NotNull String email, String msgMail) {
        this.idUser = idUser;
        this.name = name;
        this.username = username;
        this.email = email;
        this.msgMail = msgMail;
    }

    public User() {
    }
}
