package dao;

import entity.LeaveCheckList;
import entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LeaveCheckListRepository extends JpaRepository<LeaveCheckList, Integer> {

    LeaveCheckList findLeaveCheckListByUserAccount(User userAccount);
}
