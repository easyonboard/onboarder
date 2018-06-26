package dto.mapper;

import dto.EventDTO;
import entity.Event;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.factory.Mappers;

@Mapper(uses = {UserMapper.class, LocationMapper.class}, nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface EventMapper extends AbstractMapper<Event, EventDTO> {

    EventMapper INSTANCE = Mappers.getMapper(EventMapper.class);
}
