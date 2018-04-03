package dto.mapper;

import dto.RoleDTO;
import entity.Role;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.factory.Mappers;

@Mapper(nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface RoleMapper extends AbstractMapper<Role, RoleDTO> {

    RoleMapper INSTANCE = Mappers.getMapper(RoleMapper.class);


//    RoleDTO mapToDTO(Role entity);
//
//    Role mapToEntity(RoleDTO roleDTO, @MappingTarget Role role);
}
