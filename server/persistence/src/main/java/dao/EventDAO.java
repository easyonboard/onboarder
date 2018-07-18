package dao;

import entity.Event;
import entity.Tutorial;
import entity.User;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
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
    public List<Event> findAllPastEvents() {
        Query q = em.createQuery("select e from Event e where e.eventDate <= :today ");
        Date date = new Date();
        q.setParameter("today", date);
        return q.getResultList();
    }
    @Transactional
    public void removeUserFromEnrolledList(User userEntity) {
        Query query = em.createQuery("select e from Event e where :user member of e.enrolledUsers ");
        query.setParameter("user", userEntity);
        try {
            List<Event> eventList = query.getResultList();
            for (Event anEventList : eventList) {
                anEventList.getEnrolledUsers().remove(userEntity);
                persistEntity(anEventList);
            }

        } catch (NoResultException e) {
            e.printStackTrace();

        }
    }
    @Transactional
    public void removeContactPersonFromEvents(User userEntity) {
        Query query = em.createQuery("select e from Event e where :user = e.contactPerson ");
        query.setParameter("user", userEntity);
        try {
            List<Event> eventList = query.getResultList();
            for (Event anEventList : eventList) {
                anEventList.setContactPerson(null);
                persistEntity(anEventList);
            }

        } catch (NoResultException e) {
            e.printStackTrace();

        }
    }
}
