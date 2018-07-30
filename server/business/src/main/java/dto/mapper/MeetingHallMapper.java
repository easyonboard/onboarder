package dto.mapper;

import dto.MeetingHallDto;
import entity.MeetingHall;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.factory.Mappers;

@Mapper(nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface MeetingHallMapper extends AbstractMapper<MeetingHall, MeetingHallDto>{
    MeetingHallMapper INSTANCE = Mappers.getMapper(MeetingHallMapper.class);
}
