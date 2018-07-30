package service;

import dao.EventRepository;
import dao.LocationRepository;
import dao.MeetingHallRepository;
import dao.UserRepository;
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

import static exception.Constants.*;

@Service
public class EventService {

    private EventMapper eventMapper = EventMapper.INSTANCE;
    private LocationMapper locationMapper = LocationMapper.INSTANCE;
    private MeetingHallMapper meetingHallMapper = MeetingHallMapper.INSTANCE;
    private UserMapper userMapper = UserMapper.INSTANCE;

    @Autowired
    private UserRepository userRepository;
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
            Optional<User> user = userRepository.findByUsername(enrolledUsersUsernames.get(i));

            if (!user.isPresent()) {
                throw new EntityNotFoundException(userNotFound(user.get().getUsername()));
            }

            UserDTO userDTO = userMapper.mapToDTO(user.get());
            enrolledUsersDTO.add(userDTO);
        }

        Optional<User> user = userRepository.findByUsername(contactPerson);
        if (!user.isPresent()) {
            throw new EntityNotFoundException(userNotFound(user.get().getUsername()));
        }
        UserDTO contactPersonEntityDTO = userMapper.mapToDTO(user.get());

        if (locationDto.getIdLocation() != null) {
            Location location = locationRepository.findOne(locationDto.getIdLocation());
            if (location == null) {
                throw new EntityNotFoundException(LOCATIONS_NOT_FOUND_EXCEPTION);
            }
            LocationDto selectedLocationDto = locationMapper.mapToDTO(location);
            eventDTO.setLocation(selectedLocationDto);
        }

        if (meetingHallDto.getIdMeetingHall() != 0) {
            MeetingHall meetingHall = meetingHallRepository.findOne(meetingHallDto.getIdMeetingHall());
            if (meetingHall == null) {
                throw new EntityNotFoundException(HALLS_NOT_FOUND_EXCEPTION);
            }
            MeetingHallDto selectedHallDTO = meetingHallMapper.mapToDTO(meetingHall);
            eventDTO.setMeetingHall(selectedHallDTO);
        }

        eventDTO.setContactPerson(contactPersonEntityDTO);
        eventDTO.setEnrolledUsers(enrolledUsersDTO);

        Event event = eventRepository.save(eventMapper.mapToNewEntity(eventDTO));
        if (event == null) {
            throw new DatabaseException(EVENT_SAVE_DATABASE_EXCEPTION);
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
            throw new EntityNotFoundException(UPCOMING_EVENTS_NOT_FOUND_EXCEPTION);
        }

        return upcoming.stream().map(eventEntity -> eventMapper.mapToDTO(eventEntity)).collect(Collectors.toList());
    }

    public List<EventDTO> getAllUpcomingEventsFilterByKeyword(String keyword) throws EntityNotFoundException {
        List<Event> upcoming = eventRepository.findAllUpcomingEventsFilterByKeyword(
                Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant()),
                keyword);
        if (upcoming.isEmpty()) {
            throw new EntityNotFoundException(UPCOMING_EVENTS_NOT_FOUND_EXCEPTION);
        }

        return upcoming.stream().map(eventEntity -> eventMapper.mapToDTO(eventEntity)).collect(Collectors.toList());
    }

    public List<EventDTO> getAllPastEvents() throws EntityNotFoundException {
        List<Event> past = eventRepository.findAllPastEvents(
                Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant()));
        if (past.isEmpty()) {
            throw new EntityNotFoundException(PAST_EVENTS_NOT_FOUND_EXCEPTION);
        }

        return past.stream().map(eventEntity -> eventMapper.mapToDTO(eventEntity)).collect(Collectors.toList());
    }

    public List<EventDTO> getAllPastEventsFilterByKeyword(String keyword) throws EntityNotFoundException {
        List<Event> past = eventRepository.findAllPastEventsFilterByKeyword(
                Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant()),
                keyword);
        if (past.isEmpty()) {
            throw new EntityNotFoundException(PAST_EVENTS_NOT_FOUND_EXCEPTION);
        }

        return past.stream().map(eventEntity -> eventMapper.mapToDTO(eventEntity)).collect(Collectors.toList());
    }

    public List<EventDTO> enrollUser(UserDTO userDTO, int eventDTO) throws EntityNotFoundException, DatabaseException {

        Optional<User> userOptional = userRepository.findByUsername(userDTO.getUsername());
        if (userOptional.isPresent()) {
            User userEntity = userOptional.get();
            Event eventEntity = eventRepository.findOne(eventDTO);
            if (eventEntity != null) {
                if (!eventEntity.getEnrolledUsers().contains(userEntity)) {
                    eventEntity.getEnrolledUsers().add(userEntity);
                    if (eventRepository.save(eventEntity) == null) {
                        throw new DatabaseException(EVENT_SAVE_DATABASE_EXCEPTION);
                    }
                }
            }
        } else {
            throw new EntityNotFoundException(userNotFound(userDTO.getUsername()));
        }

        return getAllUpcomingEvents();
    }

    public List<LocationDto> getAllLocations() throws EntityNotFoundException {

        List<Location> locationList = locationRepository.findAll();
        if (locationList.isEmpty()) {
            throw new EntityNotFoundException(LOCATIONS_NOT_FOUND_EXCEPTION);
        }

        return locationMapper.entitiesToDTOs(locationList);
    }

    public List<MeetingHallDto> getAllMeetingHalls() throws EntityNotFoundException {

        List<MeetingHall> meetingHallList = meetingHallRepository.findAll();
        if (meetingHallList.isEmpty()) {
            throw new EntityNotFoundException(HALLS_NOT_FOUND_EXCEPTION);
        }

        return meetingHallMapper.entitiesToDTOs(meetingHallList);
    }


}
