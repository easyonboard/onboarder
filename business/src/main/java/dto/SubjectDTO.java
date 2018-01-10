package dto;

import java.util.List;

public class SubjectDTO {
    private int idSubject;

    private List<CourseDTO> containedByCourses;

    private List<MaterialDTO> materials;

    public int getIdSubject() {
        return idSubject;
    }

    private String description;

    private String name;

    private int numberOfDays;

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
