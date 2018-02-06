package dto.mapper;

import dto.MaterialDTO;
import entity.Material;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.factory.Mappers;

@Mapper(uses = SubjectWithoutCoursesAndMaterialsMapper.class, nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface MaterialMapper extends AbstractMapper<Material,MaterialDTO> {
    MaterialMapper INSTANCE=Mappers.getMapper(MaterialMapper.class);


//    List<MaterialDTO> materialsToMaterialDTOs(List<Material> entities);
//
//    MaterialDTO mapToDTO(Material entity);
//
//    Material mapToEntity(MaterialDTO materialDTO, @MappingTarget Material material);

}
