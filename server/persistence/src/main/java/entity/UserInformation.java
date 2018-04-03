package entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

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
    @Column
    private String building;
    @Column
    private String floor;
    @Column
    private String project;
    @Column
    private Date startDate;

    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "user_buddy_id")
    private User buddyUser;

    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "user_account")
    private User userAccount;

    @Column(columnDefinition="NUMBER(1)")
    private boolean mailSent;



    public UserInformation() {

    }

    public UserInformation(String team, String building, String store, String project, Date startDate, User userBuddy) {
        this.team = team;
        this.building = building;
        this.floor = store;
        this.project = project;
        this.startDate = startDate;
        this.buddyUser = userBuddy;
    }

    public String getTeam() {
        return team;
    }

    public String getBuilding() {
        return building;
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

    public void setBuilding(String building) {
        this.building = building;
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

    public boolean isMailSent() {
        return mailSent;
    }

    public void setMailSent(boolean mailSent) {
        this.mailSent = mailSent;
    }

}
