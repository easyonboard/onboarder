package entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import entity.enums.DepartmentType;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
@Table(name = "USER_INFORMATION")
public class UserInformation implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int idUserInformation;
    @Column
    private String team;

    @ManyToOne
    private Location location;

    @Column
    private String floor;
    @Column
    private String project;
    @Column
    private Date startDate;

    @Column
    @Enumerated(EnumType.STRING)
    private DepartmentType department;

    @ManyToOne (cascade = CascadeType.PERSIST)
    @JoinColumn(name = "user_buddy_id")
    private User buddyUser;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_account")
    private User userAccount;

    public UserInformation() {
    }

    public UserInformation(String team, Location location, String floor, String project, Date startDate, User userBuddy, Integer mailSent) {
        this.team = team;
        this.location = location;
        this.floor = floor;
        this.project = project;
        this.startDate = startDate;
        this.buddyUser = userBuddy;
    }

    public String getTeam() {
        return team;
    }



    public String getFloor() {
        return floor;
    }

    public String getProject() {
        return project;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setTeam(String team) {
        this.team = team;
    }


    public void setFloor(String floor) {
        this.floor = floor;
    }

    public void setProject(String project) {
        this.project = project;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }


    public User getBuddyUser() {
        return buddyUser;
    }

    public void setBuddyUser(User buddyUser) {
        this.buddyUser = buddyUser;
    }

    public int getIdUserInformation() {
        return idUserInformation;
    }

    public void setIdUserInformation(int idUserInformation) {
        this.idUserInformation = idUserInformation;
    }

    public User getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(User userAccount) {
        this.userAccount = userAccount;
    }

    public DepartmentType getDepartment() {
        return department;
    }

    public void setDepartment(DepartmentType department) {
        this.department = department;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }
}
