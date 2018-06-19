package dto.mapper;

import dto.MeetingHallDTO;
import entity.MeetingHall;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.factory.Mappers;

@Mapper(nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface MeetingHallMapper extends AbstractMapper<MeetingHall, MeetingHallDTO>{
    MeetingHallMapper INSTANCE = Mappers.getMapper(MeetingHallMapper.class);
}
