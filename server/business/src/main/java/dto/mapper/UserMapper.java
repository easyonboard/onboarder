package dto.mapper;

import dto.UserDTO;
import entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.factory.Mappers;

@Mapper(uses = {CourseWithoutUserAndSubjectMapper.class, RoleMapper.class},nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface UserMapper extends AbstractMapper<User,UserDTO> {

    UserMapper INSTANCE=Mappers.getMapper(UserMapper.class);


//    List<UserDTO> usersToUserDTOs(List<User> entities);
//
//    UserDTO mapToDTO(User entity);
//
//    User mapToEntity(UserDTO userDTO, @MappingTarget User user);
}
