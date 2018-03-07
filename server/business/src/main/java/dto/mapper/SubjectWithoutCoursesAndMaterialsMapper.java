package dto.mapper;

import dto.SubjectDTO;
import entity.Subject;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@Mapper(nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface SubjectWithoutCoursesAndMaterialsMapper extends AbstractMapper<Subject, SubjectDTO> {
    SubjectWithoutCoursesAndMaterialsMapper INSTANCE = Mappers.getMapper(SubjectWithoutCoursesAndMaterialsMapper.class);

    @Mappings({
            @Mapping(target = "materials",ignore = true),
            @Mapping(target = "containedByCourse", ignore = true),
    })
    SubjectDTO mapToDTO(Subject entity);

    @Mappings({
            @Mapping(target = "materials",ignore = true),
            @Mapping(target = "containedByCourse", ignore = true),
    })
    Subject mapToEntity(SubjectDTO subjectDTO, @MappingTarget Subject subject);
}
