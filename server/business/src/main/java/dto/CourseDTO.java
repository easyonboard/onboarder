package dto;

import java.util.List;

public class CourseDTO {
    private Integer idCourse;
    private String titleCourse;
    private String overview;
    private String keywords;


    private List<UserDTO> contactPersons;

    private List<UserDTO> owners;


    private List<UserDTO> enrolledUsers;


    public Integer getIdCourse() {
        return idCourse;
    }

    public void setIdCourse(Integer idCourse) {
        this.idCourse = idCourse;
    }

    public String getTitleCourse() {
        return titleCourse;
    }

    public void setTitleCourse(String titleCourse) {
        this.titleCourse = titleCourse;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public List<UserDTO> getContactPersons() {
        return contactPersons;
    }

    public void setContactPersons(List<UserDTO> contactPersons) {
        this.contactPersons = contactPersons;
    }

    public List<UserDTO> getOwners() {
        return owners;
    }

    public void setOwners(List<UserDTO> owners) {
        this.owners = owners;
    }

    public List<UserDTO> getEnrolledUsers() {
        return enrolledUsers;
    }

    public void setEnrolledUsers(List<UserDTO> enrolledUsers) {
        this.enrolledUsers = enrolledUsers;
    }
}
