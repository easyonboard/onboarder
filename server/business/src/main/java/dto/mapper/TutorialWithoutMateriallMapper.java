package dto.mapper;

import dto.TutorialDto;
import entity.Tutorial;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@Mapper(uses = {UserMapper.class}, nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface TutorialWithoutMateriallMapper extends AbstractMapper<Tutorial, TutorialDto> {

    TutorialWithoutMateriallMapper INSTANCE = Mappers.getMapper(TutorialWithoutMateriallMapper.class);

    @Mappings({
            @Mapping(target = "materials",ignore = true)
    })
    TutorialDto mapToDTO(Tutorial entity);

    @Mappings({
            @Mapping(target = "materials",ignore = true),
    })
    Tutorial mapToEntity(TutorialDto tutorialDto, @MappingTarget Tutorial tutorial);

}