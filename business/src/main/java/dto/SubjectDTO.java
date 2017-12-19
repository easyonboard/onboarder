package dto;

import entity.enums.MaterialType;

import javax.persistence.ManyToMany;
import java.util.List;

public class SubjectDTO {
    private int idSubject;

    private List<CourseDTO> containedByCourses;

    private List<MaterialDTO> materials;

    public int getIdSubject() {
        return idSubject;
    }

    public void setIdSubject(int idSubject) {
        this.idSubject = idSubject;
    }

    public List<CourseDTO> getContainedByCourses() {
        return containedByCourses;
    }

    public void setContainedByCourses(List<CourseDTO> containedByCourses) {
        this.containedByCourses = containedByCourses;
    }

    public List<MaterialDTO> getMaterials() {
        return materials;
    }

    public void setMaterials(List<MaterialDTO> materials) {
        this.materials = materials;
    }

}
