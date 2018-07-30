package controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import dto.EventDTO;
import dto.LocationDto;
import dto.MeetingHallDto;
import dto.UserDTO;
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
            EventDTO eventDTO = mapper.convertValue(node.get("event"), EventDTO.class);
            MeetingHallDto meetingHall = mapper.convertValue(node.get("hall"), MeetingHallDto.class);
            List<String> enrolledUsers = mapper.convertValue(node.get("enrolledPersons"), List.class);
            String contactPerson = mapper.convertValue(node.get("contactPersons"), String.class);
            LocationDto locationDto = mapper.convertValue(node.get("location"), LocationDto.class);

            return new ResponseEntity<>(
                    eventService.addEvent(eventDTO, enrolledUsers, contactPerson, locationDto, meetingHall),
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
    public ResponseEntity<List<EventDTO>> getPastEvents(@RequestParam(name = "keyword") Optional<String> keyword) {

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
    public ResponseEntity<List<EventDTO>> getUpcomingEvents(@RequestParam(name = "keyword") Optional<String> keyword) {

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
    public ResponseEntity<Object> enrollUser(@RequestBody String courseJson) {

        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode node = mapper.readTree(courseJson);
            int eventDTO = mapper.convertValue(node.get("eventID"), Integer.class);
            UserDTO userDTO = mapper.convertValue(node.get("user"), UserDTO.class);
            try {
                return new ResponseEntity<>(eventService.enrollUser(userDTO, eventDTO), HttpStatus.OK);
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
}
