package entity;


import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;

@Entity
public class Course implements Serializable {
    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE)
    private Integer idCourse;

    @Column
    private String titleCourse;

    @Lob
    @Size(min=500)
    @Column
    private String overview;

    @Column
    private String keywords;


    @ManyToMany( targetEntity = User.class)
    @JoinTable(name="course_contactPerson",joinColumns = @JoinColumn(name = "idCourse"), inverseJoinColumns = @JoinColumn(name = "idUser"))
    private List<User> contactPersons;

    @ManyToMany(targetEntity = User.class)
    @JoinTable(name="course_owner",joinColumns = @JoinColumn(name = "idCourse"), inverseJoinColumns = @JoinColumn(name = "idUser"))
    private List<User> owners;

    @ManyToMany(targetEntity = User.class)
    @JoinTable(name="course_enrolledUser",joinColumns = @JoinColumn(name = "idCourse"), inverseJoinColumns = @JoinColumn(name = "idUser"))
    private List<User> enrolledUsers;

    public Course() {
    }

    public Course(Integer id,String titleCourse,String overview, String keywords) {
        this.idCourse = id;
        this.overview = overview;
        this.titleCourse=titleCourse;
        this.keywords= keywords;
    }

    public Integer getIdCourse() {
        return idCourse;
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

    public List<User> getContactPersons() {
        return contactPersons;
    }

    public void setContactPersons(List<User> contactPersons) {
        this.contactPersons = contactPersons;
    }

    public void setId(int id) {
        this.idCourse = id;
    }

    public List<User> getOwners() {
        return owners;
    }

    public void setOwners(List<User> owners) {
        this.owners = owners;
    }

    public List<User> getEnrolledUsers() {
        return enrolledUsers;
    }

    public void setEnrolledUsers(List<User> enrolledUsers) {
        this.enrolledUsers = enrolledUsers;
    }
}
