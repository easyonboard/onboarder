package dto;

import java.util.List;

public class TutorialDto {

    private Integer idTutorial;

    private String titleTutorial;

    private String overview;

    private String keywords;

    private Boolean isDraft;

    private List<UserDto> contactPersons;

    private List<TutorialMaterialDto> tutorialMaterials;

    public TutorialDto() {
    }

    public TutorialDto(Integer idTutorial, String titleTutorial, String overview, String keywords) {
        this.idTutorial = idTutorial;
        this.titleTutorial = titleTutorial;
        this.overview = overview;
        this.keywords = keywords;
    }

    public TutorialDto(Integer idTutorial, String titleTutorial, String overview, String keywords, List<UserDto> contactPersons, List<TutorialMaterialDto> tutorialMaterials) {
        this.idTutorial = idTutorial;
        this.titleTutorial = titleTutorial;
        this.overview = overview;
        this.keywords = keywords;
        this.contactPersons = contactPersons;
        this.tutorialMaterials = tutorialMaterials;
    }

    public TutorialDto(Integer idTutorial, String titleTutorial, String overview, String keywords, Boolean isDraft, List<UserDto> contactPersons, List<TutorialMaterialDto> tutorialMaterials) {
        this.idTutorial = idTutorial;
        this.titleTutorial = titleTutorial;
        this.overview = overview;
        this.keywords = keywords;
        this.isDraft = isDraft;
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

    public List<UserDto> getContactPersons() {
        return contactPersons;
    }

    public void setContactPersons(List<UserDto> contactPersons) {
        this.contactPersons = contactPersons;
    }

    public List<TutorialMaterialDto> getTutorialMaterials() {
        return tutorialMaterials;
    }

    public void setTutorialMaterials(List<TutorialMaterialDto> tutorialMaterials) {
        this.tutorialMaterials = tutorialMaterials;
    }
}
