package dto;

import java.util.List;

public class TutorialDTO {

    private Integer idTutorial;

    private String titleTutorial;

    private String overview;

    private String keywords;

    private Boolean isDraft;

    private List<UserDTO> contactPersons;

    private List<TutorialMaterialDTO> tutorialMaterials;

    public TutorialDTO() {
    }

    public TutorialDTO(Integer idTutorial, String titleTutorial, String overview, String keywords) {
        this.idTutorial = idTutorial;
        this.titleTutorial = titleTutorial;
        this.overview = overview;
        this.keywords = keywords;
    }

    public TutorialDTO(Integer idTutorial, String titleTutorial, String overview, String keywords, List<UserDTO> contactPersons, List<TutorialMaterialDTO> tutorialMaterials) {
        this.idTutorial = idTutorial;
        this.titleTutorial = titleTutorial;
        this.overview = overview;
        this.keywords = keywords;
        this.contactPersons = contactPersons;
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

    public Boolean getDraft() {
        return isDraft;
    }

    public void setDraft(Boolean draft) {
        isDraft = draft;
    }

    public List<UserDTO> getContactPersons() {
        return contactPersons;
    }

    public void setContactPersons(List<UserDTO> contactPersons) {
        this.contactPersons = contactPersons;
    }

    public List<TutorialMaterialDTO> getTutorialMaterials() {
        return tutorialMaterials;
    }

    public void setTutorialMaterials(List<TutorialMaterialDTO> tutorialMaterials) {
        this.tutorialMaterials = tutorialMaterials;
    }
}
