package dao;

import entity.LeaveCheckList;
import entity.User;

import javax.persistence.NoResultException;
import javax.persistence.Query;

public class LeaveCheckListDAO extends AbstractDAO<LeaveCheckList> {
//    private static final String LEAVE_CHECKLIST_NOT_FOUND_EXCEPTION="User has no Leave Check List registered";
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
