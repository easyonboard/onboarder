package dto.mapper;

import dto.LeaveCheckListDto;
import entity.LeaveCheckList;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.factory.Mappers;

@Mapper(uses = {UserMapper.class},nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface LeaveCheckListMapper extends AbstractMapper<LeaveCheckList, LeaveCheckListDto>{
    LeaveCheckListMapper INSTANCE= Mappers.getMapper(LeaveCheckListMapper.class);

}
