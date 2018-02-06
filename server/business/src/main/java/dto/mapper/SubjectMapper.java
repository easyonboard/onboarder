package dto.mapper;

import dto.SubjectDTO;
import entity.Subject;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.factory.Mappers;

@Mapper(uses = {MaterialMapper.class,CourseWithoutUserAndSubjectMapper.class},nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface SubjectMapper extends AbstractMapper<Subject,SubjectDTO>{
    SubjectMapper INSTANCE=Mappers.getMapper(SubjectMapper.class);


//    List<SubjectDTO> subjectsToSubjectDTOs(List<Subject> entities);
//
//    SubjectDTO mapToDTO(Subject subject);
//
//    Subject mapToEntity(SubjectDTO subjectDTO, @MappingTarget Subject subject);

}
