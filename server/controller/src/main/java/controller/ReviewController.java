package controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import dto.CourseDTO;
import dto.ReviewDTO;
import dto.UserDTO;
import exception.CourseNotFoundException;
import exception.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import service.ReviewService;

import java.io.IOException;

@Controller
public class ReviewController {

    @Autowired
    private ReviewService reviewService;


    @CrossOrigin(origins = "http://localhost:4200")
    @RequestMapping(value = "/course/addReview", method = RequestMethod.POST)
    public ResponseEntity addReview(@RequestParam String str){

        try {
            reviewService.addReview(getUser(str), getCourse(str),getReview(str));
            return  new ResponseEntity(HttpStatus.OK);
        } catch (UserNotFoundException | CourseNotFoundException | IOException e) {
            return new ResponseEntity(e,HttpStatus.BAD_REQUEST);
        }
    }


    private UserDTO getUser(String str) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode node = mapper.readTree(str);
        return mapper.convertValue(node.get("user"), UserDTO.class);
    }

    private CourseDTO getCourse(String str) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode node = mapper.readTree(str);
        return mapper.convertValue(node.get("course"), CourseDTO.class);

    }
    private ReviewDTO getReview(String str) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode node = mapper.readTree(str);
        return mapper.convertValue(node.get("review"), ReviewDTO.class);

    }

}
