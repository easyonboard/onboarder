package dto.mapper;

import dto.TutorialDTO;
import entity.Tutorial;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.factory.Mappers;

@Mapper(uses = {UserMapper.class, TutorialMaterialMapper.class}, nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface TutorialMapper extends AbstractMapper<Tutorial, TutorialDTO> {

    TutorialMapper INSTANCE = Mappers.getMapper(TutorialMapper.class);

}
