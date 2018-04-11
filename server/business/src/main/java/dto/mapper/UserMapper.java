package dto.mapper;

import dto.UserDTO;
import entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.factory.Mappers;

@Mapper(uses = {CourseWithoutUserAndSubjectMapper.class, RoleMapper.class, UserInformationMapper.class},nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface UserMapper extends AbstractMapper<User, UserDTO> {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    @Override
    User mapToNewEntity(UserDTO userDTO);

//    List<UserDTO> usersToUserDTOs(List<User> entities);
//
//    UserDTO mapToDTO(User entity);
//
//    User mapToEntity(UserDTO userDTO, @MappingTarget User user);
}
