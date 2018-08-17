package dao;

import entity.Event;
import entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface EventRepository extends JpaRepository<Event, Integer> {

    /**
     * returns list of upcoming events, events after today date
     * @param today: date
     * @return list of all upcoming events
     */
    List<Event> findByEventDateAfter(@Param("today")Date today);

    /**
     * returns list of past events, events before today date
     * @param eventDate: today date
     * @return all past events
     */
    List<Event> findByEventDateBefore(Date eventDate);

    /**
     *  all upcoming event with specific keyword
     * @param today: today Date
     * @param keyword: string
     * @return list of upcoming events filtered
     */
    List<Event> findByEventDateAfterAndKeywordsContainingIgnoreCase(@Param("today")Date today,@Param("keyword") String keyword);

    /**
     *  all past event with specific keyword
     * @param today: Date
     * @param keyword: String
     * @return list of past events
     */
    List<Event> findByEventDateBeforeAndKeywordsContainingIgnoreCase(@Param("today")Date today,@Param("keyword") String keyword);

    @Query("select e from Event e where :user member of e.enrolledUsers")
    List<Event> removeUserFromEnrolledList(@Param("user") User user);

    @Query("select e from Event e where :user = e.contactPerson")
    List<Event> removeContactPersonFromEvents(@Param("user")User user);


}
