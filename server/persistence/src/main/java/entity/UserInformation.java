package entity;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;

@Entity
@Table(name = "USER_INFORMATION")
public class UserInformation implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int idUser;
    @Column
    private String team;
    @Column
    private String building;
    @Column
    private String store;
    @Column
    private String project;
    @Column
    private Date startDate;
    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "user_buddy_id")
    private User buddyUser;

    public UserInformation() {
    }

    public UserInformation(String team, String building, String store, String project, Date startDate, User userBuddy) {
        this.team = team;
        this.building = building;
        this.store = store;
        this.project = project;
        this.startDate = startDate;
        this.buddyUser = userBuddy;
    }

    public int getIdUser() {
        return idUser;
    }

    public String getTeam() {
        return team;
    }

    public String getBuilding() {
        return building;
    }

    public String getStore() {
        return store;
    }

    public String getProject() {
        return project;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public void setTeam(String team) {
        this.team = team;
    }

    public void setBuilding(String building) {
        this.building = building;
    }

    public void setStore(String store) {
        this.store = store;
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
}
