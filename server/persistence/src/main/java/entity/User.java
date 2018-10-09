package entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import entity.enums.RoleType;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
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

    @ManyToOne
    @JoinColumn(name = "idDepartment")
    private Department department;


    @ManyToOne
    @JoinColumn(name = "mate_id", referencedColumnName = "idUser")
    private User mate;

    @JsonBackReference
    @OneToMany(mappedBy = "mate",cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<User> mateForUsers;


    @Column
    private String team;

    @ManyToOne
    private Location location;
    @Column
    private String floor;
    @Column
    private String project;

    @Enumerated(EnumType.STRING)
    @Column
    private RoleType role;


    @JsonBackReference(value = "user-event-contact-person")
    @OneToMany(mappedBy = "contactPerson", cascade = CascadeType.ALL)
    private List<Event> events;

}
