package dto;
import entity.Location;
import entity.enums.DepartmentType;

import java.util.Date;

public class UserInformationDto {

    private int idUserInformation;

    private String team;
    private Location location;
    private String floor;
    private String project;
    private Date startDate;
    private DepartmentType department;
    private int mailSent;

    private UserDto buddyUser;
    private UserDto userAccount;

    public UserInformationDto() {
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

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public UserDto getBuddyUser() {
        return buddyUser;
    }

    public void setBuddyUser(UserDto buddyUser) {
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

    public UserDto getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(UserDto userAccount) {
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
