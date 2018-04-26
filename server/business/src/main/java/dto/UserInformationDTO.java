package dto;

import entity.enums.DepartmentType;

import java.util.Date;

public class UserInformationDTO {

    private int idUserInformation;

    private String team;
    private String building;
    private String floor;
    private String project;
    private Date startDate;
    private DepartmentType department;
    private int mailSent;

    private UserDTO buddyUser;
    private UserDTO userAccount;

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
        return floor;
    }

    public void setFloor(String floor) {
        this.floor = floor;
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

    public DepartmentType getDepartment() {
        return department;
    }

    public void setDepartment(DepartmentType department) {
        this.department = department;
    }

    public int getMailSent() {
        return mailSent;
    }

    public void setMailSent(int mailSent) {
        this.mailSent = mailSent;
    }
}
