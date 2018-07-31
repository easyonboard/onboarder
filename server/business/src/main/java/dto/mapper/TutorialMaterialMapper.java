package dto.mapper;

import dto.TutorialMaterialDto;
import entity.TutorialMaterial;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.factory.Mappers;

@Mapper(uses = {TutorialWithoutMateriallMapper.class}, nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)

public interface TutorialMaterialMapper extends AbstractMapper<TutorialMaterial, TutorialMaterialDto> {

    TutorialMaterialMapper INSTANCE = Mappers.getMapper(TutorialMaterialMapper.class);

}
