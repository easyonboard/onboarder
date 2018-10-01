package entity;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;

@Data
@Entity
public class Tutorial implements Serializable {

    public static final String FIND_TUTORIAL_BY_ID = "Tutorial.findTutorialById";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer idTutorial;
    @Column
    private String titleTutorial;
    @Lob
    @Size(max = 500)
    @Column
    private String overview;
    @Column
    private String keywords;
    @Column
    private Boolean isDraft;
    @ManyToMany(targetEntity = User.class)
    @JoinTable(name = "tutorial_contactPerson", joinColumns = @JoinColumn(name = "idTutorial"), inverseJoinColumns = @JoinColumn(name = "idUser"))
    private List<User> contactPersons;
    @OneToMany(mappedBy = "tutorial", cascade = CascadeType.ALL)
    private List<TutorialMaterial> tutorialMaterials;

    public Tutorial() {
    }
}
