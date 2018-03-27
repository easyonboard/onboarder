package dao;

import entity.UserInformation;
import org.springframework.stereotype.Service;

@Service
public class UserInformationDAO extends AbstractDAO<UserInformation> {


    @Override
    public Class<UserInformation> getEntityClass() {
        return null;
    }

}
