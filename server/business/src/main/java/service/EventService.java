package service;

import dao.EventRepository;
import dao.LocationRepository;
import dao.MeetingHallRepository;
import dao.UserDAO;
import dto.EventDTO;
import dto.LocationDto;
import dto.MeetingHallDto;
import dto.UserDTO;
import dto.mapper.EventMapper;
import dto.mapper.LocationMapper;
import dto.mapper.MeetingHallMapper;
import dto.mapper.UserMapper;
import entity.Event;
import entity.MeetingHall;
import entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class EventService {

    private EventMapper eventMapper = EventMapper.INSTANCE;
    private LocationMapper locationMapper = LocationMapper.INSTANCE;
    private MeetingHallMapper meetingHallMapper = MeetingHallMapper.INSTANCE;
    private UserMapper userMapper = UserMapper.INSTANCE;

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private LocationRepository locationRepository;

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private MeetingHallRepository meetingHallRepository;

    public EventDTO addEvent(EventDTO eventDTO, List<String> enrolledUsersUsernames, String contactPerson,
                             LocationDto location, MeetingHallDto meetingHall) {

        List<UserDTO> enrolledUsersDTO = new ArrayList<>();

        for (int i = 0; i < enrolledUsersUsernames.size(); i++) {
            UserDTO userDTO = userMapper.mapToDTO(userDAO.findUserByUsername(enrolledUsersUsernames.get(i)).get());
            enrolledUsersDTO.add(userDTO);
        }

        UserDTO contactPersonEntityDTO = userMapper.mapToDTO(userDAO.findUserByUsername(contactPerson).get());
        if (location.getIdLocation() != null) {
            LocationDto selectedLocationDto = locationMapper.mapToDTO(
                    locationRepository.findOne(location.getIdLocation()));
            eventDTO.setLocation(selectedLocationDto);
        }
        if (meetingHall.getIdMeetingHall() != 0) {
            MeetingHallDto selectedHallDTO = meetingHallMapper.mapToDTO(
                    meetingHallRepository.findOne(meetingHall.getIdMeetingHall()));
            eventDTO.setMeetingHall(selectedHallDTO);
        }
        eventDTO.setContactPerson(contactPersonEntityDTO);
        eventDTO.setEnrolledUsers(enrolledUsersDTO);

        return eventMapper.mapToDTO(eventRepository.save(eventMapper.mapToNewEntity(eventDTO)));
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

    public List<EventDTO> getAllUpcomingEvents() {

        List<Event> upcoming = eventRepository.findAllUpcomingEvents(Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant()));
        return upcoming.stream().map(
                eventEntity -> eventMapper.mapToDTO(eventEntity)).collect(Collectors.toList());

    }

    public List<EventDTO> getAllPastEvents() {

        return eventRepository.findAllPastEvents(Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant())).stream().map(
                eventEntity -> eventMapper.mapToDTO(eventEntity)).collect(Collectors.toList());

    }

    public List<EventDTO> enrollUser(UserDTO userDTO, int eventDTO) {

        Optional<User> userOptional = userDAO.findUserByUsername(userDTO.getUsername());
        if (userOptional.isPresent()) {
            User userEntity = userOptional.get();
            Event eventEntity = eventRepository.findOne(eventDTO);
            if (eventEntity != null) {
                if (!eventEntity.getEnrolledUsers().contains(userEntity)) {
                    eventEntity.getEnrolledUsers().add(userEntity);
                    eventRepository.save(eventEntity);
                }
            }
        }
        return getAllUpcomingEvents();
    }

    public List<LocationDto> getAllLocations() {

        return locationMapper.entitiesToDTOs(locationRepository.findAll());

    }

    public List<MeetingHallDto> getAllMeetingHalls() {

        return meetingHallMapper.entitiesToDTOs(meetingHallRepository.findAll());
    }

    /**
     * metodele nu sunt folosite pentru ca filtrarea locatiilor si a meeting hall-urilor e facuta pe front
     * bine de luat in considerare ca exemplu de folosire al JpaReository-urilor
     */
    public LocationDto getLocationByHallName(String hallName) {

        MeetingHall meetingHall = meetingHallRepository.findByHallName(hallName);
        return locationMapper.mapToDTO(meetingHall.getLocation());
    }

    public List<MeetingHallDto> getMeetingHallsByLocation(String location) {

        return meetingHallMapper.entitiesToDTOs(meetingHallRepository.findByLocation(location));
    }
}
