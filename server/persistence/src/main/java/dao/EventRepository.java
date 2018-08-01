package dao;

import entity.Event;
import entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface EventRepository extends JpaRepository<Event, Integer> {

    @Query("select e from Event e where e.eventDate > :today")
    List<Event> findAllUpcomingEvents(@Param("today")Date today);

    @Query("select e from Event e where e.eventDate > :today and e.keywords like %:keyword%")
    List<Event> findAllUpcomingEventsFilterByKeyword(@Param("today")Date today,@Param("keyword") String keyword);

    @Query("select e from Event e where e.eventDate <= :today")
    List<Event> findAllPastEvents(@Param("today") Date today);

    @Query("select e from Event e where e.eventDate <= :today and e.keywords like %:keyword%")
    List<Event> findAllPastEventsFilterByKeyword(@Param("today")Date today,@Param("keyword") String keyword);

    @Query("select e from Event e where :user member of e.enrolledUsers")
    List<Event> removeUserFromEnrolledList(@Param("user") User user);

    @Query("select e from Event e where :user = e.contactPerson")
    List<Event> removeContactPersonFromEvents(@Param("user")User user);



}
