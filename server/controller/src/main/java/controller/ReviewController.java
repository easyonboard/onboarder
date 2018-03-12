package controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import dto.CourseDTO;
import dto.ReviewDTO;
import exception.CourseNotFoundException;
import exception.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import service.CourseService;
import service.ReviewService;

import java.io.IOException;

@Controller
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private CourseService courseService;

    @CrossOrigin(origins = "http://localhost:4200")
    @RequestMapping(value = "/course/addReview", method = RequestMethod.POST)
    public ResponseEntity addReview(@RequestBody ReviewDTO review){

        try {
            reviewService.addReview(review);
            return new ResponseEntity(HttpStatus.OK);
        } catch (UserNotFoundException | CourseNotFoundException  e) {
            return new ResponseEntity(e,HttpStatus.BAD_REQUEST);
        }
    }
    @CrossOrigin(origins = "http://localhost:4200")
    @RequestMapping(value = "/generalRating", method = RequestMethod.POST)
    public ResponseEntity getGeneralRatingCourse(@RequestBody String str) {
        try {
            return new ResponseEntity(courseService.getGeneralRating(getCourse(str)), HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity(e,HttpStatus.BAD_REQUEST);
        }
    }

    //
    @CrossOrigin(origins = "http://localhost:4200")
    @RequestMapping(value = "/allReviews", method = RequestMethod.POST)
    public ResponseEntity getAllReviews(@RequestBody CourseDTO course){
        return new ResponseEntity(reviewService.getAllReviews(course), HttpStatus.OK);
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @RequestMapping(value = "/voteUp", method = RequestMethod.POST)
    public ResponseEntity voteUp(@RequestBody ReviewDTO review){
        return new ResponseEntity(reviewService.voteUp(review), HttpStatus.OK);
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
