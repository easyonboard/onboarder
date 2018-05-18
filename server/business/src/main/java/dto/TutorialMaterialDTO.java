package dto;

import entity.Tutorial;
import entity.enums.MaterialType;

public class TutorialMaterialDTO {

    private Integer idTutorialMaterial;

    private MaterialType materialType;
    private String link;
    private byte[] fileMaterial;
    private String title;
    private String description;
    private TutorialDTO tutorial;

    public TutorialMaterialDTO(){}

    public TutorialMaterialDTO(Integer idTutorialMaterial, MaterialType materialType, String link, byte[] fileMaterial, String title, String description, TutorialDTO tutorial) {
        this.idTutorialMaterial = idTutorialMaterial;
        this.materialType = materialType;
        this.link = link;
        this.fileMaterial = fileMaterial;
        this.title = title;
        this.description = description;
        this.tutorial = tutorial;
    }

    public Integer getIdTutorialMaterial() {
        return idTutorialMaterial;
    }

    public void setIdTutorialMaterial(Integer idTutorialMaterial) {
        this.idTutorialMaterial = idTutorialMaterial;
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

    public TutorialDTO getTutorial() {
        return tutorial;
    }

    public void setTutorial(TutorialDTO tutorial) {
        this.tutorial = tutorial;
    }
}
