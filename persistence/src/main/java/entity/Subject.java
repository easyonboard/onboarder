package entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;

@Entity
public class Subject implements Serializable {

    @Id
    @GeneratedValue(strategy= GenerationType.SEQUENCE)
    private int idSubject;

    @ManyToMany(mappedBy = "subjects", targetEntity = Course.class)
    private List<Course> containedByCourses;

    @ManyToMany(cascade = CascadeType.ALL, targetEntity = Material.class)
    @JoinTable(name="subject_material",joinColumns = @JoinColumn(name = "idSubject"), inverseJoinColumns = @JoinColumn(name = "idMaterial"))
    private List<Material> materials;

    @Lob
    @Column
    private String description;

    @NotNull
    @Column
    private String name;

    @NotNull
    @Column
    private int numberOfDays;



    public int getIdSubject() {
        return idSubject;
    }


    public List<Course> getContainedByCourses() {
        return containedByCourses;
    }

    public void setContainedByCourses(List<Course> containedByCourses) {
        this.containedByCourses = containedByCourses;
    }

    public List<Material> getMaterials() {
        return materials;
    }

    public void setMaterials(List<Material> materials) {
        this.materials = materials;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumberOfDays() {
        return numberOfDays;
    }

    public void setNumberOfDays(int numberOfDays) {
        this.numberOfDays = numberOfDays;
    }
}
