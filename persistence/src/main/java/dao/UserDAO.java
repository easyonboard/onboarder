package dao;


import entity.User;
import org.springframework.stereotype.Service;

@Service
public class UserDAO extends AbstractDAO<User>{

    @Override
    public Class<User> getEntityClass() {
        return User.class;
    }
}
