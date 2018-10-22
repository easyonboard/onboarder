package entity;

import entity.enums.MaterialType;
import lombok.Data;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;


@Data
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

    @Column(length = 2147483647)
    @Lob
    private byte[] fileMaterial;

    @Column
    private String title;

    @Column
    @Size(max = 2500)
    private String description;

    @ManyToOne
    @JoinColumn(name = "idTutorial")
    private Tutorial tutorial;


}
