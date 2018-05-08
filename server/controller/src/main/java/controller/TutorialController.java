package controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.media.sound.InvalidDataException;
import dto.TutorialDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import service.TutorialService;

import java.io.IOException;
import java.util.List;

@Controller
public class TutorialController {

    @Autowired
    private TutorialService tutorialService;

    @CrossOrigin(origins = "http://localhost:4200")
    @RequestMapping(value = "/tutorials", method = RequestMethod.GET)
    public ResponseEntity<List<TutorialDTO>> allCourses(@RequestParam(value = "keyword", required = false) String keyword) {
        if (keyword!=null && keyword.length()>0){
            return new ResponseEntity<>(tutorialService.filterByKeyword(keyword), HttpStatus.OK);
        }
        return new ResponseEntity<>(tutorialService.getAllCourses(), HttpStatus.OK);
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @RequestMapping(value = "/tutorials/addTutorial", method = RequestMethod.POST)
    public ResponseEntity addTutorial(@RequestBody String courseJson) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            JsonNode node = null;
            node = mapper.readTree(courseJson);
            TutorialDTO tutorialDTO = mapper.convertValue(node.get("tutorial"), TutorialDTO.class);
            List<Integer> ownersIds = mapper.convertValue(node.get("ownersIds"), List.class);
            List<Integer> contactPersonsId = mapper.convertValue(node.get("contactPersonsId"), List.class);

            return new ResponseEntity(tutorialService.addTutorial(tutorialDTO, ownersIds, contactPersonsId), HttpStatus.OK);
        } catch (InvalidDataException e) {
            return new ResponseEntity(e, HttpStatus.BAD_REQUEST);
        } catch (IOException e) {
            return new ResponseEntity(e, HttpStatus.BAD_REQUEST);
        }
    }
}
