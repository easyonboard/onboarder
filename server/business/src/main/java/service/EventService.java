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
import entity.Location;
import entity.MeetingHall;
import entity.User;
import exception.types.DatabaseException;
import exception.types.EntityNotFoundException;
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
                             LocationDto locationDto,
                             MeetingHallDto meetingHallDto) throws DatabaseException, EntityNotFoundException {

        List<UserDTO> enrolledUsersDTO = new ArrayList<>();

        for (int i = 0; i < enrolledUsersUsernames.size(); i++) {
            Optional<User> user = userDAO.findUserByUsername(enrolledUsersUsernames.get(i));
            if (!user.isPresent()) {
                throw new EntityNotFoundException("" + user.get().getUsername());
            }

            UserDTO userDTO = userMapper.mapToDTO(user.get());
            enrolledUsersDTO.add(userDTO);
        }

        Optional<User> user = userDAO.findUserByUsername(contactPerson);
        if (!user.isPresent()) {
            throw new EntityNotFoundException("" + user.get().getUsername());
        }
        UserDTO contactPersonEntityDTO = userMapper.mapToDTO(user.get());

        if (locationDto.getIdLocation() != null) {
            Location location = locationRepository.findOne(locationDto.getIdLocation());
            if (location == null) {
                throw new EntityNotFoundException("");
            }
            LocationDto selectedLocationDto = locationMapper.mapToDTO(location);
            eventDTO.setLocation(selectedLocationDto);
        }
        if (meetingHallDto.getIdMeetingHall() != 0) {
            MeetingHall meetingHall = meetingHallRepository.findOne(meetingHallDto.getIdMeetingHall());
            if (meetingHall == null) {
                throw new EntityNotFoundException("");
            }
            MeetingHallDto selectedHallDTO = meetingHallMapper.mapToDTO(meetingHall);
            eventDTO.setMeetingHall(selectedHallDTO);
        }
        eventDTO.setContactPerson(contactPersonEntityDTO);
        eventDTO.setEnrolledUsers(enrolledUsersDTO);

        Event event = eventRepository.save(eventMapper.mapToNewEntity(eventDTO));
        if (event == null) {
            throw new DatabaseException("");
        }

        return eventMapper.mapToDTO(event);
    }

    private List<String> extractUsernamesFromEmails(List<String> nameUsernamesEmail) {

        List<String> usernames = new ArrayList<>();
        StringTokenizer st;

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

    public List<EventDTO> getAllUpcomingEvents() throws EntityNotFoundException {

        List<Event> upcoming = eventRepository.findAllUpcomingEvents(
                Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant()));
        if (upcoming.isEmpty()) {
            throw new EntityNotFoundException("");
        }
        return upcoming.stream().map(eventEntity -> eventMapper.mapToDTO(eventEntity)).collect(Collectors.toList());

    }

    public List<EventDTO> getAllPastEvents() throws EntityNotFoundException {

        List<Event> past = eventRepository.findAllPastEvents(
                Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant()));
        if (past.isEmpty()) {
            throw new EntityNotFoundException("");
        }
        return past.stream().map(eventEntity -> eventMapper.mapToDTO(eventEntity)).collect(Collectors.toList());

    }

    public List<EventDTO> enrollUser(UserDTO userDTO, int eventDTO) throws EntityNotFoundException {

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
        } else {
            throw new EntityNotFoundException("");
        }

        return getAllUpcomingEvents();
    }

    public List<LocationDto> getAllLocations() throws EntityNotFoundException {

        List<Location> locationList = locationRepository.findAll();
        if (locationList.isEmpty()) {
            throw new EntityNotFoundException("Locations have not been found in the DB");
        }

        return locationMapper.entitiesToDTOs(locationList);

    }

    public List<MeetingHallDto> getAllMeetingHalls() throws EntityNotFoundException {

        List<MeetingHall> meetingHallList = meetingHallRepository.findAll();
        if (!meetingHallList.isEmpty()) {
            throw new EntityNotFoundException("Meeting halls have not been found in the DB");
        }

        return meetingHallMapper.entitiesToDTOs(meetingHallList);
    }
}
