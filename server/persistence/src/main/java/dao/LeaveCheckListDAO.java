package dao;

import entity.LeaveCheckList;
import entity.User;
import org.springframework.stereotype.Service;

import javax.persistence.NoResultException;
import javax.persistence.Query;

@Service
public class LeaveCheckListDAO extends AbstractDAO<LeaveCheckList> {
//
    @Override
    public Class<LeaveCheckList> getEntityClass() {
        return LeaveCheckList.class;
    }


    public LeaveCheckList findLeaveCheckListByUser(User userEntity){

        Query query=em.createQuery("select cl from LeaveCheckList cl where cl.userAccount=:user");
        query.setParameter("user", userEntity);
        try{
            return (LeaveCheckList) query.getSingleResult();
        }catch (NoResultException e){

         return null;
        }

    }

}
