package dto;

import entity.enums.MaterialType;

import java.util.List;

public class MaterialDTO {
    private Integer idMaterial;

    private MaterialType materialType;

    private String link;

    private byte[] fileMaterial;

    private List<CourseDTO> containedByCours;


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

    public List<CourseDTO> getContainedByCours() {
        return containedByCours;
    }

    public void setContainedByCours(List<CourseDTO> containedByCours) {
        this.containedByCours = containedByCours;
    }
}

