package dto.mapper;

import dto.TutorialDto;
import entity.Tutorial;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.factory.Mappers;

@Mapper(uses = {UserMapper.class, TutorialMaterialMapper.class}, nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface TutorialMapper extends AbstractMapper<Tutorial, TutorialDto> {

    TutorialMapper INSTANCE = Mappers.getMapper(TutorialMapper.class);

}
