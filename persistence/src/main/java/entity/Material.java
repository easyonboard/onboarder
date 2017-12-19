package entity;

import entity.enums.MaterialType;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

@Entity
public class Material implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer idMaterial;

    @NotNull
    @Enumerated(EnumType.STRING)
    private MaterialType materialType;

    @Column
    private String link;

    @Lob
    @Column
    private byte[] fileMaterial;

    @ManyToMany(mappedBy = "materials")
    private List<Subject> containedBySubjects;

    @Column
    private String title;

    @Column
    private String description;


    public Integer getIdMaterial() {
        return idMaterial;
    }

    public MaterialType getMaterialType() {
        return materialType;
    }

    public void setMaterialType(MaterialType materialType) {
        this.materialType = materialType;
    }

    public List<Subject> getContainedBySubjects() {
        return containedBySubjects;
    }

    public void setContainedBySubjects(List<Subject> containedBySubjects) {
        this.containedBySubjects = containedBySubjects;
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
