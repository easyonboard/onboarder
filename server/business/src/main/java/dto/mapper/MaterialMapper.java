package dto.mapper;

import dto.MaterialDto;
import entity.Material;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.factory.Mappers;

@Mapper(uses = {TutorialWithoutMateriallMapper.class}, nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)

public interface MaterialMapper extends AbstractMapper<Material, MaterialDto> {

    MaterialMapper INSTANCE = Mappers.getMapper(MaterialMapper.class);

}
