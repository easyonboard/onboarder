package dto;

import java.util.Date;

public class UserInformationDTO {

    private int id;
    private String team;
    private String building;
    private String store;
    private Date startDate;
    private UserDTO buddyUser;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getStore() {
        return store;
    }

    public void setStore(String store) {
        this.store = store;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public UserDTO getBuddyUser() {
        return buddyUser;
    }

    public void setBuddyUser(UserDTO buddyUser) {
        this.buddyUser = buddyUser;
    }
}
