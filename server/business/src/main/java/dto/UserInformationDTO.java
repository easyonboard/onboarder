package dto;

import java.util.Date;

public class UserInformationDTO {

    private int idUserInformation;
    private String team;
    private String building;
    private String store;
    private String project;
    private Date startDate;
    private UserDTO buddyUser;
    private UserDTO userAccount;
    private boolean mailSent;

    public UserInformationDTO() {
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
        return store;
    }

    public void setFloor(String store) {
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

    public int getIdUserInformation() {
        return idUserInformation;
    }

    public void setIdUserInformation(int idUserInformation) {
        this.idUserInformation = idUserInformation;
    }

    public UserDTO getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(UserDTO userAccount) {
        this.userAccount = userAccount;
    }

    public boolean isMailSent() {
        return mailSent;
    }

    public void setMailSent(boolean mailSent) {
        this.mailSent = mailSent;
    }
}
