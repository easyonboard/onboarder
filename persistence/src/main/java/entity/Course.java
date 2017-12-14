package entity;


import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
public class Course implements Serializable {
    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE)
    private Integer idCourse;

    @Column
    private String overview;

    @ManyToMany(cascade = CascadeType.ALL,targetEntity = Subject.class)
    @JoinTable(name="course_subject",joinColumns = @JoinColumn(name = "idCourse"), inverseJoinColumns = @JoinColumn(name = "idSubject"))
    private List<Subject> subjects;

    @ManyToMany(cascade = CascadeType.ALL, targetEntity = User.class)
    @JoinTable(name="course_contactPerson",joinColumns = @JoinColumn(name = "idCourse"), inverseJoinColumns = @JoinColumn(name = "idUser"))
    private List<User> contactPersons;

    @ManyToMany(cascade = CascadeType.ALL, targetEntity = User.class)
    @JoinTable(name="course_owner",joinColumns = @JoinColumn(name = "idCourse"), inverseJoinColumns = @JoinColumn(name = "idUser"))
    private List<User> owners;

    @ManyToMany(cascade = CascadeType.ALL, targetEntity = Material.class)
    @JoinTable(name="course_material",joinColumns = @JoinColumn(name = "idCourse"), inverseJoinColumns = @JoinColumn(name = "idMaterial"))
    private List<Material> materials;

    @ManyToMany(cascade = {CascadeType.ALL, CascadeType.REMOVE}, targetEntity = User.class)
    @JoinTable(name="course_enrolledUser",joinColumns = @JoinColumn(name = "idCourse"), inverseJoinColumns = @JoinColumn(name = "idUser"))
    private List<User> enrolledUsers;

    public Course() {
    }

    public Course(Integer id,String overview) {
        this.idCourse = id;
        this.overview = overview;
    }

    public Integer getIdCourse() {
        return idCourse;
    }


    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public List<Subject> getSubjects() {
        return subjects;
    }

    public void setSubjects(List<Subject> subjects) {
        this.subjects = subjects;
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

    public List<Material> getMaterials() {
        return materials;
    }

    public void setMaterials(List<Material> materials) {
        this.materials = materials;
    }

    public List<User> getEnrolledUsers() {
        return enrolledUsers;
    }

    public void setEnrolledUsers(List<User> enrolledUsers) {
        this.enrolledUsers = enrolledUsers;
    }
}