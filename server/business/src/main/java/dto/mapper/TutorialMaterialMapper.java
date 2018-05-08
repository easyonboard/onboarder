package dto.mapper;

import dto.TutorialMaterialDTO;
import entity.TutorialMaterial;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.factory.Mappers;

@Mapper(uses = {UserMapper.class}, nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)

public interface TutorialMaterialMapper extends AbstractMapper<TutorialMaterial, TutorialMaterialDTO> {

    TutorialMaterialMapper INSTANCE = Mappers.getMapper(TutorialMaterialMapper.class);

}
