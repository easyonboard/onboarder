package dto;

import java.util.Date;

public class UserInformationDTO {

    private int idUser;
    private String team;
    private String building;
    private String store;
    private String project;
    private Date startDate;
    private UserDTO buddyUser;

    public UserInformationDTO() {
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
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

    public String getProject() {
        return project;
    }

    public void setProject(String project) {
        this.project = project;
    }
}
