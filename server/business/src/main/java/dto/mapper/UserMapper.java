package dto.mapper;

import dto.UserDto;
import entity.User;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@Mapper(nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface UserMapper extends AbstractMapper<User, UserDto> {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    @Override
    User mapToNewEntity(UserDto userDto);


}
