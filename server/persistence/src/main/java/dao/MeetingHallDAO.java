package dao;

import entity.MeetingHall;
import org.springframework.stereotype.Service;

import javax.persistence.Query;
import java.util.List;

@Service
public class MeetingHallDAO extends AbstractDAO<MeetingHall> {
    @Override
    public Class<MeetingHall> getEntityClass() {
        return MeetingHall.class;
    }

    public List<MeetingHall> getAllRooms(){

        Query q=em.createQuery("select l from MeetingHall l");
        return q.getResultList();

    }
}
