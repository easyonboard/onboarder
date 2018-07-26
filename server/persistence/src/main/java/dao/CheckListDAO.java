package dao;

import entity.CheckList;
import org.springframework.stereotype.Service;

@Service
public class CheckListDAO extends AbstractDAO<CheckList> {

    @Override
    public Class<CheckList> getEntityClass() {
        return CheckList.class;
    }

//    eusebio --- found no usage for method, commented it.
//    @Transactional
//    public void setValue(User user, String fieldName, boolean value) {
//
//        try {
//            CheckList checkListForUser = checkListRepository.findByUserAccount(user);
//            Field field = null;
//            field = CheckList.class.getDeclaredField(fieldName);
//
//            field.setAccessible(true);
//            field.set(checkListForUser, value);
//
//            this.persistEntity(checkListForUser);
//        } catch (NoSuchFieldException e) {
//            e.printStackTrace();
//        } catch (IllegalAccessException e) {
//            e.printStackTrace();
//        }
//    }
}
