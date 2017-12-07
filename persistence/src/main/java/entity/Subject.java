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

    public int getIdSubject() {
        return idSubject;
    }


    public List<Course> getContainedByCourses() {
        return containedByCourses;
    }

    public void setContainedByCourses(List<Course> containedByCourses) {
        this.containedByCourses = containedByCourses;
    }
}
