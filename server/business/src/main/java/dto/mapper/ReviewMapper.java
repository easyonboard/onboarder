package dto.mapper;

import dto.ReviewDTO;
import entity.Review;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.factory.Mappers;

@Mapper(uses = {CourseWithoutUserAndSubjectMapper.class, UserMapper.class},nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface ReviewMapper extends AbstractMapper<Review, ReviewDTO> {
    ReviewMapper INSTANCE= Mappers.getMapper(ReviewMapper.class);
}
