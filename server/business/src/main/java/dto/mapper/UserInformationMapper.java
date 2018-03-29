package dto.mapper;

import dto.UserInformationDTO;
import entity.UserInformation;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.factory.Mappers;

@Mapper(uses = {UserMapper.class},nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface UserInformationMapper extends AbstractMapper<UserInformation, UserInformationDTO> {

    UserInformationMapper INSTANCE= Mappers.getMapper(UserInformationMapper.class);
}
