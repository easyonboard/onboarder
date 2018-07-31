package dto.mapper;

import dto.RoleDto;
import entity.Role;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.factory.Mappers;

@Mapper(nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface RoleMapper extends AbstractMapper<Role, RoleDto> {

    RoleMapper INSTANCE = Mappers.getMapper(RoleMapper.class);


//    RoleDto mapToDTO(Role entity);
//
//    Role mapToEntity(RoleDto roleDTO, @MappingTarget Role role);
}
