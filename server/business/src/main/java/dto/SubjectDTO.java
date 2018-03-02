package dto;

import java.util.List;

public class SubjectDTO {
    private int idSubject;

    private CourseDTO containedByCourse;

    private List<MaterialDTO> materials;

    public int getIdSubject() {
        return idSubject;
    }

    private String description;

    private String name;

    private int numberOfDays;

    private int position;

    public void setIdSubject(int idSubject) {
        this.idSubject = idSubject;
    }

    public CourseDTO getContainedByCourse() {
        return containedByCourse;
    }

    public void setContainedByCourse(CourseDTO containedByCourse) {
        this.containedByCourse = containedByCourse;
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

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}
