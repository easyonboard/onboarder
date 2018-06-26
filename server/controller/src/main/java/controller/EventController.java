package controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.media.sound.InvalidDataException;
import dto.EventDTO;
import dto.TutorialDTO;
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
    public ResponseEntity addEvent(@RequestBody String courseJson) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            JsonNode node = null;
            node = mapper.readTree(courseJson);
            EventDTO eventDTO = mapper.convertValue(node.get("event"), EventDTO.class);
            List<String> enrolledUsers = mapper.convertValue(node.get("enrolledUsers"), List.class);
            List<String> contactPerson = mapper.convertValue(node.get("contactPerson"), List.class);

            return new ResponseEntity(eventService.addEvent(eventDTO, enrolledUsers, contactPerson), HttpStatus.OK);
        } catch (InvalidDataException e) {
            return new ResponseEntity(e, HttpStatus.BAD_REQUEST);
        } catch (IOException e) {
            return new ResponseEntity(e, HttpStatus.BAD_REQUEST);
        }
    }
}
