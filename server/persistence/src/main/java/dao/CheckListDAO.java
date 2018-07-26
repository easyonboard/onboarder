package dao;

import entity.CheckList;
import entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.lang.reflect.Field;

@Service
public class CheckListDAO extends AbstractDAO<CheckList>  {

    @Autowired
    private CheckListRepository checkListRepository;

    @Override
    public Class<CheckList> getEntityClass() {

        return CheckList.class;
    }


    @Transactional
    public void setValue(User user, String fieldName, boolean value) {

        try {
            CheckList checkListForUser = checkListRepository.findByUserAccount(user);
            Field field = null;
            field = CheckList.class.getDeclaredField(fieldName);

            field.setAccessible(true);
            field.set(checkListForUser, value);

            this.persistEntity(checkListForUser);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
