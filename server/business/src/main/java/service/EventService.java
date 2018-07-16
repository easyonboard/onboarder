package service;

import dao.EventDAO;
import dao.LocationDAO;
import dao.MeetingHallDAO;
import dao.UserDAO;
import dto.EventDTO;
import dto.LocationDTO;
import dto.MeetingHallDTO;
import dto.UserDTO;
import dto.mapper.EventMapper;
import dto.mapper.LocationMapper;
import dto.mapper.MeetingHallMapper;
import entity.Event;
import entity.Location;
import entity.MeetingHall;
import entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.StringTokenizer;
import java.util.stream.Collectors;

@Service
public class EventService {

    private EventMapper eventMapper = EventMapper.INSTANCE;
    private LocationMapper locationMapper = LocationMapper.INSTANCE;
    private MeetingHallMapper meetingHallMapper = MeetingHallMapper.INSTANCE;

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private LocationDAO locationDAO;

    @Autowired
    private EventDAO eventDAO;

    @Autowired
    private MeetingHallDAO meetingHallDAO;

    public EventDTO addEvent(EventDTO eventDTO, List<String> enrolledUsersEmails, String contactPerson, LocationDTO location, MeetingHallDTO meetingHall){
        List<String> enrolledUsersUsernames = extractUsernamesFromEmails(enrolledUsersEmails);

        Event event = eventMapper.mapToNewEntity(eventDTO);

        List<User> enrolledUsers = new ArrayList<>();

        for (int i = 0; i < enrolledUsersUsernames.size(); i++) {
            User user = userDAO.findUserByUsername(enrolledUsersUsernames.get(i)).get();
            enrolledUsers.add(user);
        }

        User contactPersonEntity = userDAO.findUserByUsername(contactPerson).get();
        if(location.getIdLocation()!=null) {
            Location selectedLocation = locationDAO.findEntity(location.getIdLocation());
            event.setLocation(selectedLocation);
        }
        if(meetingHall.getIdMeetingHall()!=0) {
            MeetingHall selectedHall = meetingHallDAO.findEntity(meetingHall.getIdMeetingHall());
            event.setMeetingHall(selectedHall);
        }

        event.setContactPerson(contactPersonEntity);
        contactPersonEntity.getEvents().add(event);
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

        return eventDAO.findAllPastEvents().stream().map(eventEntity->eventMapper.mapToDTO(eventEntity)).collect(Collectors.toList());

    }

    public List<EventDTO> enrollUser(UserDTO userDTO, int eventDTO) {
        Optional<User> userOptional=userDAO.findUserByUsername(userDTO.getUsername());
        if(userOptional.isPresent()){
            User userEntity=userOptional.get();
            Event eventEntity=eventDAO.findEntity(eventDTO);
            if(eventEntity!=null){

                   eventEntity.getEnrolledUsers().add(userEntity);
                   eventDAO.persistEntity(eventEntity);

            }
        }
        return getAllUpcomingEvents();

    }

    public List<LocationDTO> getAllLocations() {

        return locationMapper.entitiesToDTOs(locationDAO.getAllLocations());

    }

    public List<MeetingHallDTO> getAllMeetingHalls() {
      return  meetingHallMapper.entitiesToDTOs(meetingHallDAO.getAllRooms());
    }
}
