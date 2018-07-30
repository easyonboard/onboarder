package entity;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;

@NamedQueries({@NamedQuery(name = Tutorial.FIND_TUTORIAL_BY_ID, query = "select t from Tutorial t where t.idTutorial=:idTutorial")})
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

    @Column(columnDefinition="NUMBER(1) default '0'")
    private Boolean isDraft;

    @ManyToMany(targetEntity = User.class)
    @JoinTable(name = "tutorial_contactPerson", joinColumns = @JoinColumn(name = "idTutorial"), inverseJoinColumns = @JoinColumn(name = "idUser"))
    private List<User> contactPersons;
    
    @OneToMany(mappedBy = "tutorial", cascade = CascadeType.ALL)
    private List<TutorialMaterial> tutorialMaterials;

    public Tutorial() {
    }

    public Tutorial(Integer idTutorial, String titleTutorial, @Size(min = 50) String overview, String keywords) {
        this.idTutorial = idTutorial;
        this.titleTutorial = titleTutorial;
        this.overview = overview;
        this.keywords = keywords;
    }

    public Tutorial(Integer idTutorial, String titleTutorial, @Size(min = 50) String overview, String keywords, List<User> contactPersons, List<TutorialMaterial> tutorialMaterials) {
        this.idTutorial = idTutorial;
        this.titleTutorial = titleTutorial;
        this.overview = overview;
        this.keywords = keywords;
        this.contactPersons = contactPersons;
        this.tutorialMaterials = tutorialMaterials;
    }


    public List<TutorialMaterial> getTutorialMaterials() {
        return tutorialMaterials;
    }

    public void setTutorialMaterials(List<TutorialMaterial> tutorialMaterials) {
        this.tutorialMaterials = tutorialMaterials;
    }

    public Integer getIdTutorial() {
        return idTutorial;
    }

    public void setIdTutorial(Integer idTutorial) {
        this.idTutorial = idTutorial;
    }

    public String getTitleTutorial() {
        return titleTutorial;
    }

    public void setTitleTutorial(String titleTutorial) {
        this.titleTutorial = titleTutorial;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public List<User> getContactPersons() {
        return contactPersons;
    }

    public void setContactPersons(List<User> contactPersons) {
        this.contactPersons = contactPersons;
    }

    public Boolean getDraft() {
        return isDraft;
    }

    public void setDraft(Boolean draft) {
        isDraft = draft;
    }
}
