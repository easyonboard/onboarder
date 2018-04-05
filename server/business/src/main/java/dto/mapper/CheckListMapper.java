package dto.mapper;

import dto.CheckListDTO;
import entity.CheckList;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.factory.Mappers;

@Mapper(uses = {UserMapper.class},nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface CheckListMapper extends AbstractMapper<CheckList, CheckListDTO>{
    CheckListMapper INSTANCE= Mappers.getMapper(CheckListMapper.class);
}


