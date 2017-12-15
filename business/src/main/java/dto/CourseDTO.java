package dto;

import java.util.List;

public class CourseDTO {
    private Integer idCourse;
    private String titleCourse;
    private String overview;
    private List<SubjectDTO> subjects;

    private List<UserDTO> contactPersons;


    private List<UserDTO> owners;

    private List<MaterialDTO> materials;

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

    public List<SubjectDTO> getSubjects() {
        return subjects;
    }

    public void setSubjects(List<SubjectDTO> subjects) {
        this.subjects = subjects;
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

    public List<MaterialDTO> getMaterials() {
        return materials;
    }

    public void setMaterials(List<MaterialDTO> materials) {
        this.materials = materials;
    }

    public List<UserDTO> getEnrolledUsers() {
        return enrolledUsers;
    }

    public void setEnrolledUsers(List<UserDTO> enrolledUsers) {
        this.enrolledUsers = enrolledUsers;
    }
}
