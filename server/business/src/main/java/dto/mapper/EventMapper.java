package dto.mapper;

import dto.EventDto;
import entity.Event;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.factory.Mappers;

@Mapper(uses = {UserMapper.class, LocationMapper.class, MeetingHallMapper.class}, nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface EventMapper extends AbstractMapper<Event, EventDto> {

    EventMapper INSTANCE = Mappers.getMapper(EventMapper.class);
}
