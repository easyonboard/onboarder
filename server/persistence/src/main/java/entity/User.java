package entity;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMsgMail() {
        return msgMail;
    }

    public void setMsgMail(String msgMail) {
        this.msgMail = msgMail;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public User getBuddyUser() {
        return buddyUser;
    }

    public void setBuddyUser(User buddyUser) {
        this.buddyUser = buddyUser;
    }

    public String getTeam() {
        return team;
    }

    public void setTeam(String team) {
        this.team = team;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public String getFloor() {
        return floor;
    }

    public void setFloor(String floor) {
        this.floor = floor;
    }

    public String getProject() {
        return project;
    }

    public void setProject(String project) {
        this.project = project;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public List<Event> getEvents() {
        return events;
    }

    public void setEvents(List<Event> events) {
        this.events = events;
    }
}
