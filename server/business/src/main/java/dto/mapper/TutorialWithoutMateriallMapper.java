package dto.mapper;

import dto.TutorialDTO;
import entity.Tutorial;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@Mapper(uses = {UserMapper.class}, nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface TutorialWithoutMateriallMapper extends AbstractMapper<Tutorial, TutorialDTO> {

    TutorialWithoutMateriallMapper INSTANCE = Mappers.getMapper(TutorialWithoutMateriallMapper.class);

    @Mappings({
            @Mapping(target = "tutorialMaterials",ignore = true)
    })
    TutorialDTO mapToDTO(Tutorial entity);

    @Mappings({
            @Mapping(target = "tutorialMaterials",ignore = true),
    })
    Tutorial mapToEntity(TutorialDTO tutorialDTO, @MappingTarget Tutorial tutorial);

}