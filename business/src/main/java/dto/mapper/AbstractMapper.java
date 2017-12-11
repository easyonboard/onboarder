package dto.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import java.util.List;

public interface AbstractMapper<E,DTO> {

    List<DTO> entitiesToDTOs(List<E> entities);

    DTO mapToDTO(E entity);

    E mapToEntity(DTO dto, @MappingTarget E entity);

    E mapToNewEntity(DTO dto);
}
