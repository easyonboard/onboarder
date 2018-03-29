package entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

@Entity
@Table(name = "app_user_info")
public class UserInfo implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int idUserInfo;

    @Column
    private String team;
    @Column
    private String building;
    @Column
    private String floor;
    @Column
    private String projectName;
    @Column
    private String buddy;
    @Column
    private Date startingDate;

    public int getIdUserInfo() {
        return idUserInfo;
    }

    public void setIdUserInfo(int idUserInfo) {
        this.idUserInfo = idUserInfo;
    }

    public String getTeam() {
        return team;
    }

    public void setTeam(String team) {
        this.team = team;
    }

    public String getBuilding() {
        return building;
    }

    public void setBuilding(String building) {
        this.building = building;
    }

    public String getFloor() {
        return floor;
    }

    public void setFloor(String floor) {
        this.floor = floor;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getBuddy() {
        return buddy;
    }

    public void setBuddy(String buddy) {
        this.buddy = buddy;
    }

    public Date getStartingDate() {
        return startingDate;
    }

    public void setStartingDate(Date startingDate) {
        this.startingDate = startingDate;
    }
    
}
