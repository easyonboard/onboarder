package entity;

import entity.enums.MaterialType;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Data
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

    @ManyToOne
    @JoinColumn(name = "idTutorial")
    private Tutorial tutorial;

    public TutorialMaterial() {
    }
}
