package dao;

import entity.Event;
import entity.Tutorial;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

@Repository
public class EventDAO extends AbstractDAO<Event> {

    @Override
    public Class<Event> getEntityClass() {
        return Event.class;
    }

    @Transactional
    public List<Event> findAllUpcomingEvents() {
        Query q = em.createQuery("select e from Event e where e.eventDate > :today ");
        Date date = new Date();
        q.setParameter("today", date);
        return q.getResultList();
    }

    @Transactional
    public List<Event> findAllUPastEvents() {
        Query q = em.createQuery("select e from Event e where e.eventDate <= :today ");
        Date date = new Date();
        q.setParameter("today", date);
        return q.getResultList();
    }
}
