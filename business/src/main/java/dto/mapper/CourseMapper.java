package dto.mapper;

import dto.CourseDTO;
import dto.UserDTO;
import entity.Course;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.factory.Mappers;
import org.springframework.web.bind.annotation.Mapping;

import java.util.List;

@Mapper(uses = {SubjectMapper.class,UserMapper.class, MaterialMapper.class}, nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface CourseMapper extends AbstractMapper<Course,CourseDTO>{

    CourseMapper INSTANCE=Mappers.getMapper(CourseMapper.class);
//    List<CourseDTO> coursesToCourseDTOs(List<Course> entities);
//
//    CourseDTO mapToDTO(Course entity);
//
//    Course mapToEntity(CourseDTO materialDTO, @MappingTarget Course course);

}
