package controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import dto.EventDTO;
import dto.LocationDTO;
import dto.MeetingHallDTO;
import dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import service.EventService;

import java.io.IOException;
import java.util.List;

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
            MeetingHallDTO meetingHall = mapper.convertValue(node.get("hall"), MeetingHallDTO.class);
            List<String> enrolledUsers = mapper.convertValue(node.get("enrolledPersons"), List.class);
            String contactPerson = mapper.convertValue(node.get("contactPersons"), String.class);
            LocationDTO locationDTO = mapper.convertValue(node.get("location"), LocationDTO.class);

            return new ResponseEntity<>(
                    eventService.addEvent(eventDTO, enrolledUsers, contactPerson, locationDTO, meetingHall),
                    HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity<>(e, HttpStatus.BAD_REQUEST);
        }
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @RequestMapping(value = "/events/pastEvent", method = RequestMethod.GET)
    public ResponseEntity<List<EventDTO>> getPastEvents() {

        return new ResponseEntity<>(eventService.getAllPastEvents(), HttpStatus.OK);

    }

    @CrossOrigin(origins = "http://localhost:4200")
    @RequestMapping(value = "/events/upcomingEvent", method = RequestMethod.GET)
    public ResponseEntity<List<EventDTO>> getUpcomingEvents() {

        return new ResponseEntity<>(eventService.getAllUpcomingEvents(), HttpStatus.OK);

    }

    @CrossOrigin(origins = "http://localhost:4200")
    @RequestMapping(value = "/events/enrollUser", method = RequestMethod.POST)
    public ResponseEntity<Object> enrollUser(@RequestBody String courseJson) {

        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode node = mapper.readTree(courseJson);
            int eventDTO = mapper.convertValue(node.get("eventID"), Integer.class);
            UserDTO userDTO = mapper.convertValue(node.get("user"), UserDTO.class);
            return new ResponseEntity<>(eventService.enrollUser(userDTO, eventDTO), HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity<>(e, HttpStatus.BAD_REQUEST);
        }

    }

    @CrossOrigin(origins = "http://localhost:4200")
    @RequestMapping(value = "locations", method = RequestMethod.GET)
    public ResponseEntity<List<dto.LocationDTO>> getAllLocations() {

        return new ResponseEntity<>(eventService.getAllLocations(), HttpStatus.OK);
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @RequestMapping(value = "meetingHalls", method = RequestMethod.GET)
    public ResponseEntity<List<dto.MeetingHallDTO>> getAllMeetingHalls() {

        return new ResponseEntity<>(eventService.getAllMeetingHalls(), HttpStatus.OK);
    }
}
