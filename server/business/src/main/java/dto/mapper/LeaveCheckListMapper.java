package dto.mapper;

import dto.LeaveCheckListDTO;
import entity.LeaveCheckList;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.factory.Mappers;

@Mapper(uses = {UserMapper.class},nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface LeaveCheckListMapper extends AbstractMapper<LeaveCheckList, LeaveCheckListDTO>{
    LeaveCheckListMapper INSTANCE= Mappers.getMapper(LeaveCheckListMapper.class);

}
