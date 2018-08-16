package service;

import dao.EventRepository;
import entity.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Scheduled service for deleting events older than 3 months
 */
@Service
public class DeleteEventScheduler {

    @Autowired
    private EventRepository eventRepository;

    /**
     * everyday at 6 a.m checks for events older than 3 months and deletes them
     */
    @Scheduled(cron = "0 0 6 * * *")
    public void deleteEventsOlderThanThreeMonths() {

        Date referenceDate = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(referenceDate);
        calendar.add(Calendar.MONTH, -3);
        List<Event> eventsToDelete = eventRepository.findByEventDateBefore(calendar.getTime());
        if (!eventsToDelete.isEmpty()) {
            for (Event anEventsToDelete : eventsToDelete) {
                eventRepository.delete(anEventsToDelete.getIdEvent());
            }
        }

    }

}
