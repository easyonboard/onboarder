package entity;

import entity.enums.MaterialType;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Entity
public class TutorialMaterial implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer idTutorialMaterial;

    @NotNull
    @Enumerated(EnumType.STRING)
    private MaterialType materialType;
    @Column
    private String link;
    @Lob
    @Column
    private byte[] fileMaterial;
    @Column
    private String title;
    @Column
    @Size(max = 2500)
    private String description;
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "idTutorial")
    private Tutorial tutorial;

    public TutorialMaterial() {
    }

    public TutorialMaterial(Integer idTutorialMaterial, @NotNull MaterialType materialType, String link, byte[] fileMaterial, String title, @Size(max = 2500) String description, Tutorial tutorial) {
        this.idTutorialMaterial = idTutorialMaterial;
        this.materialType = materialType;
        this.link = link;
        this.fileMaterial = fileMaterial;
        this.title = title;
        this.description = description;
        this.tutorial = tutorial;
    }

    public Tutorial getTutorial() {
        return tutorial;
    }

    public void setTutorial(Tutorial tutorial) {
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
}
