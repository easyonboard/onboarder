package dto;

import entity.enums.MaterialType;
import lombok.Data;

@Data
public class TutorialMaterialDto {

    private Integer idTutorialMaterial;

    private MaterialType materialType;
    private String link;
    private byte[] fileMaterial;
    private String title;
    private String description;
    private TutorialDto tutorial;

}
