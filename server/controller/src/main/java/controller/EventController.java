package controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import dto.EventDto;
import dto.LocationDto;
import dto.MeetingHallDto;
import dto.UserDto;
import exception.types.DatabaseException;
import exception.types.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import service.EventService;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Controller
public class EventController {

    @Autowired
    private EventService eventService;

    @CrossOrigin(origins = "http://localhost:4200")
    @RequestMapping(value = "/events/addEvent", method = RequestMethod.POST)
    public ResponseEntity<Object> addEvent(@RequestBody String courseJson) {

        ObjectMapper mapper = new ObjectMapper();
        try {
            JsonNode node = null;
            node = mapper.readTree(courseJson);
            EventDto eventDto = mapper.convertValue(node.get("event"), EventDto.class);
            MeetingHallDto meetingHall = mapper.convertValue(node.get("hall"), MeetingHallDto.class);
            JsonNode nodeEnrolledPersonMsgMails = node.get("enrolledPersons");
            List<String> enrolledPersonMsgMails = mapper.readValue(nodeEnrolledPersonMsgMails.toString(), new TypeReference<List<String>>(){});
            JsonNode nodeContactPersonMsgMails = node.get("contactPersons");
            List<String> contactPersonMsgMail = mapper.readValue(nodeContactPersonMsgMails.toString(), new TypeReference<List<String>>(){});
            LocationDto locationDto = mapper.convertValue(node.get("location"), LocationDto.class);

            return new ResponseEntity<>(
                    eventService.addEvent(eventDto, enrolledPersonMsgMails, contactPersonMsgMail.get(0), locationDto, meetingHall),
                    HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity<>(e, HttpStatus.BAD_REQUEST);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(e, HttpStatus.NOT_FOUND);
        } catch (DatabaseException e) {
            return new ResponseEntity<>(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @RequestMapping(value = "/events/pastEvent", method = RequestMethod.GET)
    public ResponseEntity<List<EventDto>> getPastEvents(@RequestParam(name = "keyword") Optional<String> keyword) {

        try {
            if(keyword.isPresent()){
                return new ResponseEntity<>(eventService.getAllPastEventsFilterByKeyword(keyword.get()), HttpStatus.OK);
            }
            return new ResponseEntity<>(eventService.getAllPastEvents(), HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity(e, HttpStatus.NOT_FOUND);
        }
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @RequestMapping(value = "/events/upcomingEvent", method = RequestMethod.GET)
    public ResponseEntity<List<EventDto>> getUpcomingEvents(@RequestParam(name = "keyword") Optional<String> keyword) {

        try {
            if(keyword.isPresent()){
                return new ResponseEntity<>(eventService.getAllUpcomingEventsFilterByKeyword(keyword.get()), HttpStatus.OK);
            }
            return new ResponseEntity<>(eventService.getAllUpcomingEvents(), HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity(e, HttpStatus.NOT_FOUND);
        }

    }

    @CrossOrigin(origins = "http://localhost:4200")
    @RequestMapping(value = "/events/enrollUser", method = RequestMethod.POST)
    public ResponseEntity enrollUser(@RequestBody String courseJson) {

        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode node = mapper.readTree(courseJson);
            int eventDTO = mapper.convertValue(node.get("eventID"), Integer.class);
            UserDto userDto = mapper.convertValue(node.get("user"), UserDto.class);
            try {
                return new ResponseEntity<>(eventService.enrollUser(userDto, eventDTO), HttpStatus.OK);
            } catch (DatabaseException e) {
                return new ResponseEntity(e, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (IOException e) {
            return new ResponseEntity(e, HttpStatus.BAD_REQUEST);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity(e, HttpStatus.NOT_FOUND);
        }
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @RequestMapping(value = "/events/unenrollUser", method = RequestMethod.POST)
    public ResponseEntity unenrollUser(@RequestBody String courseJson) {

        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode node = mapper.readTree(courseJson);
            int eventDTO = mapper.convertValue(node.get("eventID"), Integer.class);
            UserDto userDto = mapper.convertValue(node.get("user"), UserDto.class);
            try {
                return new ResponseEntity<>(eventService.unenrollUser(userDto, eventDTO), HttpStatus.OK);
            } catch (DatabaseException e) {
                return new ResponseEntity(e, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (IOException e) {
            return new ResponseEntity<>(e, HttpStatus.BAD_REQUEST);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity(e, HttpStatus.NOT_FOUND);
        }
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @RequestMapping(value = "/events/isEnrolled", method = RequestMethod.POST)
    public ResponseEntity isUserEnrolled(@RequestBody String courseJson) {

        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode node = mapper.readTree(courseJson);
            int eventDTO = mapper.convertValue(node.get("eventID"), Integer.class);
            UserDto userDto = mapper.convertValue(node.get("user"), UserDto.class);
            return new ResponseEntity<>(eventService.getStatusEnrollment(userDto, eventDTO), HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity<>(e, HttpStatus.BAD_REQUEST);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity(e, HttpStatus.NOT_FOUND);
        }
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @RequestMapping(value = "locations", method = RequestMethod.GET)
    public ResponseEntity<List<LocationDto>> getAllLocations() {

        try {
            return new ResponseEntity<>(eventService.getAllLocations(), HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity(e, HttpStatus.NOT_FOUND);
        }

    }

    @CrossOrigin(origins = "http://localhost:4200")
    @RequestMapping(value = "meetingHalls", method = RequestMethod.GET)
    public ResponseEntity<List<MeetingHallDto>> getAllMeetingHalls() {

        try {
            return new ResponseEntity<>(eventService.getAllMeetingHalls(), HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity(e, HttpStatus.NOT_FOUND);
        }
    }


    @CrossOrigin(origins = "http://localhost:4200")
    @RequestMapping(value = "/deleteEvent/upcoming", method = RequestMethod.POST)
    public ResponseEntity<List<EventDto>> deleteUpcomingEvent(@RequestBody EventDto eventDto) {
        try {
            return new ResponseEntity<>(eventService.deleteUpcomingEvent(eventDto.getIdEvent()), HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity(e, HttpStatus.NOT_FOUND);
        }
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @RequestMapping(value = "/deleteEvent/past", method = RequestMethod.POST)
    public ResponseEntity<List<EventDto>> deletePastEvent(@RequestBody EventDto eventDto) {
        try {
            return new ResponseEntity<>(eventService.deletePastEvent(eventDto.getIdEvent()), HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity(e, HttpStatus.NOT_FOUND);
        }

    }

}
