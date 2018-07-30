package dto.mapper;

import dto.LocationDto;
import entity.Location;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.factory.Mappers;

@Mapper(nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface LocationMapper  extends AbstractMapper<Location, LocationDto> {

        LocationMapper INSTANCE = Mappers.getMapper(LocationMapper.class);
}







