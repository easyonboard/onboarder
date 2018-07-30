package dao;

import entity.Event;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;


/**
  * @author - albc
  *
  */

/** Un exemplu de test pentru modulul de persistence
 * Asa va trebui sa se faca pentru fiecare clasa
 */
@RunWith(JUnit4.class)
public class EventRepositoryTest {

    private static EventRepository eventRepositoryMock;

    @BeforeClass
    public static void setup() {

        eventRepositoryMock = mock(EventRepository.class);
    }

    @Test
    public void findAllUpcomingEvents() {

        Event futureEvent = new Event();

        LocalDate futureDate = LocalDate.now().plus(4, ChronoUnit.DAYS);
        futureEvent.setTitleEvent("Future");
        futureEvent.setEventDate(Date.from(futureDate.atStartOfDay(ZoneId.systemDefault()).toInstant()));
        futureEvent.setIdEvent(2);

        List<Event> list = new ArrayList<>();
        list.add(futureEvent);

        when(eventRepositoryMock.findAllUpcomingEvents(any(Date.class))).thenReturn(list);

        int eventsCount = eventRepositoryMock.findAllUpcomingEvents(Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant())).size();

        assertEquals(1, eventsCount);
    }

    @Test
    public void findAllPastEvents() {
        Event pastEvent = new Event();

        LocalDate pastDate = LocalDate.now().minus(4, ChronoUnit.DAYS);
        pastEvent.setTitleEvent("Past");
        pastEvent.setEventDate(Date.from(pastDate.atStartOfDay(ZoneId.systemDefault()).toInstant()));
        pastEvent.setIdEvent(1);

        List<Event> list = new ArrayList<>();
        list.add(pastEvent);

        when(eventRepositoryMock.findAllUpcomingEvents(any(Date.class))).thenReturn(list);

        int eventsCount = eventRepositoryMock.findAllUpcomingEvents(Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant())).size();

        assertEquals(1, eventsCount);
    }

    @Test
    public void removeUserFromEnrolledList() {

    }

    @Test
    public void removeContactPersonFromEvents() {

    }
}