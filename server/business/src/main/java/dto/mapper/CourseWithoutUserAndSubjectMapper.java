package dto.mapper;

import dto.CourseDTO;
import entity.Course;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@Mapper(nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface CourseWithoutUserAndSubjectMapper extends AbstractMapper<Course, CourseDTO> {

    CourseWithoutUserAndSubjectMapper INSTANCE = Mappers.getMapper(CourseWithoutUserAndSubjectMapper.class);


    @Mappings({
            @Mapping(target = "contactPersons", ignore = true),
            @Mapping(target = "owners", ignore = true),
            @Mapping(target = "enrolledUsers", ignore = true)
    })
    CourseDTO mapToDTO(Course entity);

    @Mappings({

            @Mapping(target = "contactPersons", ignore = true),
            @Mapping(target = "owners", ignore = true),
            @Mapping(target = "enrolledUsers", ignore = true)
    })
    Course mapToEntity(CourseDTO materialDTO, @MappingTarget Course course);
}
