package entity;

import com.sun.istack.internal.Nullable;

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
    @Nullable
    private String link;

    @Lob
    @Nullable
    @Column
    private byte[] fileMaterial;

    @ManyToMany(mappedBy = "materials")
    private List<Course> containedByCours;

    public Integer getIdMaterial() {
        return idMaterial;
    }

    public MaterialType getMaterialType() {
        return materialType;
    }

    public void setMaterialType(MaterialType materialType) {
        this.materialType = materialType;
    }

    public List<Course> getContainedByCours() {
        return containedByCours;
    }

    public void setContainedByCours(List<Course> containedByCours) {
        this.containedByCours = containedByCours;
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
}
