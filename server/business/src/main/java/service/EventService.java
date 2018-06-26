package service;

import dao.EventDAO;
import dao.UserDAO;
import dto.EventDTO;
import dto.mapper.EventMapper;
import entity.Event;
import entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.stream.Collectors;

@Service
public class EventService {

    private EventMapper eventMapper = EventMapper.INSTANCE;

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private EventDAO eventDAO;

    public EventDTO addEvent(EventDTO eventDTO, List<String> enrolledUsersEmails, List<String> contactPersonEmails){
        List<String> enrolledUsersUsernames = extractUsernamesFromEmails(enrolledUsersEmails);
        List<String> contactPersonUsernames = extractUsernamesFromEmails(contactPersonEmails);
        String contactPersonUsername = contactPersonUsernames.get(0);
        Event event = eventMapper.mapToEntity(eventDTO,new Event());

        List<User> enrolledUsers = new ArrayList<>();

        for (int i = 0; i < enrolledUsersUsernames.size(); i++) {
            User user = userDAO.findUserByUsername(enrolledUsersUsernames.get(i)).get();
            enrolledUsers.add(user);
        }

        User contactPerson = userDAO.findUserByUsername(contactPersonUsername).get();

        event.setContactPerson(contactPerson);
        contactPerson.getEvents().add(event);
        event.setEnrolledUsers(enrolledUsers);

        return eventMapper.mapToDTO(eventDAO.persistEntity(event));
    }


    private List<String> extractUsernamesFromEmails(List<String> nameUsernamesEmail) {
        List<String> usernames = new ArrayList<>();
        StringTokenizer st;
        String username;

        for (int i = 0; i < nameUsernamesEmail.size(); i++) {
            st = new StringTokenizer(nameUsernamesEmail.get(i), "()");

            if (st.hasMoreTokens()) {
                st.nextToken();
            }
            if (st.hasMoreTokens()) {
                usernames.add(st.nextToken());
            }
        }

        return usernames;
    }

    public List<EventDTO> getAllUpcomingEvents(){

      return eventDAO.findAllUpcomingEvents().stream().map(eventEntity->eventMapper.mapToDTO(eventEntity)).collect(Collectors.toList());

    }
    public List<EventDTO> getAllPastEvents(){

        return eventDAO.findAllUPastEvents().stream().map(eventEntity->eventMapper.mapToDTO(eventEntity)).collect(Collectors.toList());

    }
}
