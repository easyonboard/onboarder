package dto.mapper;

import dto.CourseDTO;
import entity.Course;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface CourseWithoutUserAndSubjectAndMaterialMapper extends AbstractMapper<Course,CourseDTO>{

    CourseWithoutUserAndSubjectAndMaterialMapper INSTANCE=Mappers.getMapper(CourseWithoutUserAndSubjectAndMaterialMapper.class);


    @Mappings({
            @Mapping(target = "subjects",ignore = true),
            @Mapping(target = "contactPersons", ignore = true),
            @Mapping(target = "owners", ignore = true),
            @Mapping(target = "materials", ignore = true),
            @Mapping(target = "enrolledUsers", ignore = true)
    })
    CourseDTO mapToDTO(Course entity);

    @Mappings({
            @Mapping(target = "subjects",ignore = true),
            @Mapping(target = "contactPersons", ignore = true),
            @Mapping(target = "owners", ignore = true),
            @Mapping(target = "materials", ignore = true),
            @Mapping(target = "enrolledUsers", ignore = true)
    })
    Course mapToEntity(CourseDTO materialDTO, @MappingTarget Course course);
}
