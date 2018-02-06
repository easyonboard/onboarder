package controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dto.CourseDTO;
import dto.SubjectDTO;
import exception.InvalidDataException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import service.SubjectService;

import java.io.IOException;

@Controller
public class SubjectController {
    @Autowired
    private SubjectService subjectService;

    @CrossOrigin(origins = "http://localhost:4200")
    @RequestMapping(value = "/subject/addSubject", method = RequestMethod.POST)
    public ResponseEntity addSubject(@RequestBody String subjectAndCourseJSON) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            JsonNode node = mapper.readTree(subjectAndCourseJSON);
            SubjectDTO subjectDTO = mapper.convertValue(node.get("subject"), SubjectDTO.class);
            CourseDTO courseDTO = mapper.convertValue(node.get("course"), CourseDTO.class);
            return new ResponseEntity(subjectService.addSubject(subjectDTO,courseDTO), HttpStatus.OK);
        } catch (JsonProcessingException e) {
            return new ResponseEntity(e, HttpStatus.BAD_REQUEST);
        } catch (IOException e) {
            return new ResponseEntity(e, HttpStatus.BAD_REQUEST);
        }
    }
}
