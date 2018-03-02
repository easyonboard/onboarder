package entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

@Entity
public class Subject implements Serializable {

    @Id
    @GeneratedValue(strategy= GenerationType.SEQUENCE)
    private int idSubject;

    @ManyToOne
    private Course containedByCourse;

    @OneToMany(cascade = CascadeType.ALL, targetEntity = Material.class)
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

    @Column
    private int position;



    public int getIdSubject() {
        return idSubject;
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

    public Course getContainedByCourse() {
        return containedByCourse;
    }

    public void setContainedByCourse(Course containedByCourse) {
        this.containedByCourse = containedByCourse;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}
