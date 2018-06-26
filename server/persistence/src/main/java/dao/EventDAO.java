package dao;

import entity.Event;
import entity.Tutorial;
import org.springframework.stereotype.Repository;

@Repository
public class EventDAO extends AbstractDAO<Event> {

    @Override
    public Class<Event> getEntityClass() {
        return Event.class;
    }
}
