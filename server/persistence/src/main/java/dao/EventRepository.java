package dao;

import entity.Event;
import entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

//@Repository
//public class EventRepository extends AbstractDAO<Event> {
//
//    @Override
//    public Class<Event> getEntityClass() {
//        return Event.class;
//    }
//
//    @Transactional
//    public List<Event> findAllUpcomingEvents() {
//        Query q = em.createQuery("select e from Event e where e.eventDate > :today ");
//        Date date = new Date();
//        q.setParameter("today", date);
//        return q.getResultList();
//    }
//
//    @Transactional
//    public List<Event> findAllPastEvents() {
//        Query q = em.createQuery("select e from Event e where e.eventDate <= :today ");
//        Date date = new Date();
//        q.setParameter("today", date);
//        return q.getResultList();
//    }
//    @Transactional
//    public void removeUserFromEnrolledList(User userEntity) {
//        Query query = em.createQuery("select e from Event e where :user member of e.enrolledUsers ");
//        query.setParameter("user", userEntity);
//        try {
//            List<Event> eventList = query.getResultList();
//            for (Event anEventList : eventList) {
//                anEventList.getEnrolledUsers().remove(userEntity);
//                persistEntity(anEventList);
//            }
//
//        } catch (NoResultException e) {
//            e.printStackTrace();
//
//        }
//    }
//    @Transactional
//    public void removeContactPersonFromEvents(User userEntity) {
//        Query query = em.createQuery("select e from Event e where :user = e.contactPerson ");
//        query.setParameter("user", userEntity);
//        try {
//            List<Event> eventList = query.getResultList();
//            for (Event anEventList : eventList) {
//                anEventList.setContactPerson(null);
//                persistEntity(anEventList);
//            }
//
//        } catch (NoResultException e) {
//            e.printStackTrace();
//
//        }
//    }
//}

public interface EventRepository extends JpaRepository<Event, Integer> {

    @Query("select e from Event e where e.eventDate > :today")
    List<Event> findAllUpcomingEvents(@Param("today")Date today);

    @Query("select e from Event e where e.eventDate <= :today")
    List<Event> findAllPastEvents(@Param("today") Date today);

    @Query("select e from Event e where :user member of e.enrolledUsers")
    User removeUserFromEnrolledList(@Param("user") User user);

    @Query("select e from Event e where :user = e.contactPerson")
    User removeContactPersonFromEvents(@Param("user")User user);
}
