package entity;

import javax.persistence.*;
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

}
