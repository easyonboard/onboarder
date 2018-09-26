package dto;

import lombok.Data;

import java.util.List;

@Data
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
}
