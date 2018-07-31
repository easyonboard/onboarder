package dto.mapper;

import dto.UserDto;
import entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.factory.Mappers;

@Mapper(uses = {RoleMapper.class, UserInformationMapper.class},nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface UserMapper extends AbstractMapper<User, UserDto> {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    @Override
    User mapToNewEntity(UserDto userDto);

//    List<UserDto> usersToUserDTOs(List<User> entities);
//
//    UserDto mapToDTO(User entity);
//
//    User mapToEntity(UserDto userDTO, @MappingTarget User user);
}
