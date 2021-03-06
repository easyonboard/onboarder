package service;

import dao.EventRepository;
import dao.LocationRepository;
import dao.MeetingHallRepository;
import dao.UserRepository;
import dto.EventDto;
import dto.LocationDto;
import dto.MeetingHallDto;
import dto.UserDto;
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
import org.apache.poi.hssf.record.formula.functions.Even;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import utilityService.MailSender;

import java.text.DateFormat;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
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

    public EventDto addEvent(EventDto eventDto, List<String> enrolledPersonMsgMails, String contactPersonMsgMail,
                             LocationDto locationDto,
                             MeetingHallDto meetingHallDto) throws DatabaseException, EntityNotFoundException {

        List<UserDto> enrolledUsersDTO = new ArrayList<>();

        for (String enrolledPersonMsgMail : enrolledPersonMsgMails) {
            Optional<User> user = userRepository.findByMsgMail(enrolledPersonMsgMail);

            if (!user.isPresent()) {
                throw new EntityNotFoundException(userNotFound(user.get().getUsername()));
            }

            UserDto userDto = userMapper.mapToDTO(user.get());
            enrolledUsersDTO.add(userDto);
        }

        Optional<User> user = userRepository.findByMsgMail(contactPersonMsgMail);
        if (!user.isPresent()) {
            throw new EntityNotFoundException(userNotFound(user.get().getUsername()));
        }
        UserDto contactPersonEntityDTO = userMapper.mapToDTO(user.get());

        if (locationDto.getIdLocation() != null) {
            Location location = locationRepository.findOne(locationDto.getIdLocation());
            if (location == null) {
                throw new EntityNotFoundException(LOCATIONS_NOT_FOUND_EXCEPTION);
            }
            LocationDto selectedLocationDto = locationMapper.mapToDTO(location);
            eventDto.setLocation(selectedLocationDto);
        }

        if (meetingHallDto.getIdMeetingHall() != 0) {
            MeetingHall meetingHall = meetingHallRepository.findOne(meetingHallDto.getIdMeetingHall());
            if (meetingHall == null) {
                throw new EntityNotFoundException(HALLS_NOT_FOUND_EXCEPTION);
            }
            MeetingHallDto selectedHallDto = meetingHallMapper.mapToDTO(meetingHall);
            eventDto.setMeetingHall(selectedHallDto);
        }

        eventDto.setContactPerson(contactPersonEntityDTO);
        eventDto.setEnrolledUsers(enrolledUsersDTO);
        if(eventDto.getEventDate().before(new Date())){
            throw new DatabaseException(EVENT_DATE_FUTURE);
        }
            Event event = eventRepository.save(eventMapper.mapToNewEntity(eventDto));
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

    public List<EventDto> getAllUpcomingEvents() throws EntityNotFoundException {

        List<Event> upcoming = eventRepository.findByEventDateAfter(
                Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant()));
        if (upcoming.isEmpty()) {
            throw new EntityNotFoundException(UPCOMING_EVENTS_NOT_FOUND_EXCEPTION);
        }

        return upcoming.stream().map(eventEntity -> eventMapper.mapToDTO(eventEntity)).collect(Collectors.toList());
    }

    public List<EventDto> getAllUpcomingEventsFilterByKeyword(String keyword) throws EntityNotFoundException {

        List<Event> upcoming = eventRepository.findByEventDateAfterAndKeywordsContainingIgnoreCase(
                Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant()), keyword);
        if (upcoming.isEmpty()) {
            throw new EntityNotFoundException(UPCOMING_EVENTS_NOT_FOUND_EXCEPTION);
        }

        return upcoming.stream().map(eventEntity -> eventMapper.mapToDTO(eventEntity)).collect(Collectors.toList());
    }

    public List<EventDto> getAllPastEvents() throws EntityNotFoundException {

        List<Event> past = eventRepository.findByEventDateBefore(
                Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant()));
        if (past.isEmpty()) {
            throw new EntityNotFoundException(PAST_EVENTS_NOT_FOUND_EXCEPTION);
        }

        return past.stream().map(eventEntity -> eventMapper.mapToDTO(eventEntity)).collect(Collectors.toList());
    }

    public List<EventDto> getAllPastEventsFilterByKeyword(String keyword) throws EntityNotFoundException {

        List<Event> past = eventRepository.findByEventDateBeforeAndKeywordsContainingIgnoreCase(
                Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant()), keyword);
        if (past.isEmpty()) {
            throw new EntityNotFoundException(PAST_EVENTS_NOT_FOUND_EXCEPTION);
        }

        return past.stream().map(eventEntity -> eventMapper.mapToDTO(eventEntity)).collect(Collectors.toList());
    }

    public List<EventDto> enrollUser(UserDto userDto, int eventDTO) throws EntityNotFoundException, DatabaseException {

        Optional<User> userOptional = userRepository.findByMsgMail(userDto.getMsgMail());
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
            throw new EntityNotFoundException(userNotFound(userDto.getUsername()));
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

    public List<EventDto> unenrollUser(UserDto userDto,
                                       int eventDTO) throws DatabaseException, EntityNotFoundException {

        Optional<User> userOptional = userRepository.findByMsgMail(userDto.getMsgMail());
        if (userOptional.isPresent()) {
            User userEntity = userOptional.get();
            Event eventEntity = eventRepository.findOne(eventDTO);
            if (eventEntity != null) {
                if (eventEntity.getEnrolledUsers().contains(userEntity)) {
                    eventEntity.getEnrolledUsers().remove(userEntity);
                    if (eventRepository.save(eventEntity) == null) {
                        throw new DatabaseException(EVENT_SAVE_DATABASE_EXCEPTION);
                    }
                }
            }
        } else {
            throw new EntityNotFoundException(userNotFound(userDto.getUsername()));
        }

        return getAllUpcomingEvents();
    }

    public Boolean getStatusEnrollment(UserDto userDto, int eventDTO) throws EntityNotFoundException {

        Optional<User> userOptional = userRepository.findByMsgMail(userDto.getMsgMail());
        if (userOptional.isPresent()) {
            User userEntity = userOptional.get();
            Event eventEntity = eventRepository.findOne(eventDTO);
            return eventEntity.getEnrolledUsers().contains(userEntity);
        } else {
            throw new EntityNotFoundException(userNotFound(userDto.getUsername()));
        }
    }

    public List<EventDto> deleteUpcomingEvent(int idEvent) throws EntityNotFoundException {
        Event eventEntity=eventRepository.findOne(idEvent);
        MailSender sender = new MailSender();
        if(eventEntity.getEnrolledUsers()!=null){
            DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
           String dateWithZeroTime;
            dateWithZeroTime = formatter.format(eventEntity.getEventDate());
            for(User u: eventEntity.getEnrolledUsers()){
                String contentMail=createEmailBodyForDeleteEvent(u.getFirstName(), eventEntity.getTitleEvent(), dateWithZeroTime);
                sender.sendMail(u.getMsgMail(), "Eveniment anulat",contentMail);
            }
        }
        eventRepository.delete(idEvent);
        return getAllUpcomingEvents();
    }
    public List<EventDto> deletePastEvent(int idEvent) throws EntityNotFoundException {
        eventRepository.delete(idEvent);
        return getAllPastEvents();
    }

    private String createEmailBodyForDeleteEvent(String employeeName, String eventName, String date) {
        ResourceBundle bundle = ResourceBundle.getBundle("email_template_delete_event", Locale.ROOT);
        String emailBody = bundle.getString("email_body");
        String formattedEmailBoddy;
        formattedEmailBoddy = MessageFormat.format(emailBody, employeeName, eventName, date);
        return formattedEmailBoddy;
    }
}
