package dto;

import entity.enums.MaterialType;

import java.util.List;

public class MaterialDTO {
    private Integer idMaterial;

    private MaterialType materialType;

    private String link;

    private byte[] fileMaterial;

    private List<SubjectDTO> containedBySubjects;

    private String title;

    private String description;

    public Integer getIdMaterial() {
        return idMaterial;
    }

    public void setIdMaterial(Integer idMaterial) {
        this.idMaterial = idMaterial;
    }

    public MaterialType getMaterialType() {
        return materialType;
    }

    public void setMaterialType(MaterialType materialType) {
        this.materialType = materialType;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public byte[] getFileMaterial() {
        return fileMaterial;
    }

    public void setFileMaterial(byte[] fileMaterial) {
        this.fileMaterial = fileMaterial;
    }

    public List<SubjectDTO> getContainedBySubjects() {
        return containedBySubjects;
    }

    public void setContainedBySubjects(List<SubjectDTO> containedBySubjects) {
        this.containedBySubjects = containedBySubjects;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

