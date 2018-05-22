package controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import dto.CourseDTO;

import dto.UserDTO;
import exception.CourseNotFoundException;
import exception.DeleteCourseException;
import exception.InvalidDataException;
import exception.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import service.CourseService;


import java.io.IOException;
import java.util.List;

@Controller
public class CourseController {

    @Autowired
    private CourseService courseService;



    @CrossOrigin(origins = "http://localhost:4200")
    @RequestMapping(value = "/courses", method = RequestMethod.GET)
    public ResponseEntity<List<CourseDTO>> allCourses(@RequestParam(value = "keyword", required = false) String keyword) {
        if (keyword!=null && keyword.length()>0){
            return new ResponseEntity<>(courseService.filterByKeyword(keyword), HttpStatus.OK);
        }
            return new ResponseEntity<>(courseService.getAllCourses(), HttpStatus.OK);
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @RequestMapping(value = "/coursesFromPage", method = RequestMethod.GET)
    public ResponseEntity<List<CourseDTO>> coursesFromPage(@RequestParam(value = "pageNumber") Integer pageNumber, @RequestParam(value = "numberOfObjectsPerPage") Integer numberOfObjectsPerPage) {
        return new ResponseEntity<>(courseService.getCoursesFromPage(pageNumber, numberOfObjectsPerPage), HttpStatus.OK);
    }


    @CrossOrigin(origins = "http://localhost:4200")
    @RequestMapping(value = "/courses/course", method = RequestMethod.GET)
    public ResponseEntity<List<CourseDTO>> searchArticlesByOverview(@RequestParam(value = "overview") String overview) {

        List<CourseDTO> searchCourses = courseService.searchByOverview(overview);
//        List<CourseDTO> courses = courseService.getAllTutorials();
//        for (int i = 0; i < courses.size(); i++) {
//            if (courses.get(i).getOverview().contains(overview)) {
//                searchCourses.add(courses.get(i));
//            }
//        }
        return new ResponseEntity<>(searchCourses, HttpStatus.OK);

    }

    @CrossOrigin(origins = "http://localhost:4200")
    @RequestMapping(value = "courses/detailedCourse", method = RequestMethod.GET)

    public ResponseEntity getDetails(@RequestParam(value = "id") Integer id) {

        try {
            CourseDTO courseById = courseService.getCourseById(id);
            return new ResponseEntity<>(courseById, HttpStatus.OK);
        } catch (CourseNotFoundException e) {
            return new ResponseEntity<>(e, HttpStatus.BAD_REQUEST);
        }

    }




    @CrossOrigin(origins = "http://localhost:4200")
    @RequestMapping(value = "/courses/isEnrolledOnCourse", method = RequestMethod.POST)
    public ResponseEntity<Boolean> isEnrolledOnCourse(@RequestParam(value = "idCourse") Integer idCourse, @RequestBody String username) throws UserNotFoundException {
        return new ResponseEntity<Boolean>(courseService.userIsEnrolledOnCourse(username, idCourse), HttpStatus.OK);
    }



    @CrossOrigin(origins = "http://localhost:4200")
    @RequestMapping(value = "/courses/updateCourse", method = RequestMethod.POST)
    public ResponseEntity updateCourse(@RequestBody String courseJson) {

        ObjectMapper mapper = new ObjectMapper();
        try {
            JsonNode node = mapper.readTree(courseJson);
            CourseDTO course = getCourse(courseJson);

            List<Integer> ownersIds = mapper.convertValue(node.get("ownersIds"), List.class);
            List<Integer> contactPersonsId = mapper.convertValue(node.get("contactPersonsId"), List.class);
            courseService.updateCourse(course, ownersIds, contactPersonsId);
            return new ResponseEntity(HttpStatus.OK);
        } catch (InvalidDataException | IOException e) {
            return new ResponseEntity(e, HttpStatus.BAD_REQUEST);
        }


    }



    @CrossOrigin(origins = "http://localhost:4200")
    @RequestMapping(value = "/deleteContactPerson", method = RequestMethod.POST)
    public ResponseEntity deleteContactPerson(@RequestBody String str) {

        try {
            courseService.deleteContactPersonForCourse(getUser(str), getCourse(str));
            return new ResponseEntity(HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity(e, HttpStatus.BAD_REQUEST);
        }

    }


    @CrossOrigin(origins = "http://localhost:4200")
    @RequestMapping(value = "/courses/deleteCourse", method = RequestMethod.POST)
    public ResponseEntity deleteCourse(@RequestBody CourseDTO course) {

        try {
            courseService.deleteCourse(course);
            return new ResponseEntity(HttpStatus.OK);
        } catch (CourseNotFoundException | DeleteCourseException e) {
            return new ResponseEntity(e, HttpStatus.BAD_REQUEST);
        }

    }

    @CrossOrigin(origins = "http://localhost:4200")
    @RequestMapping(value = "/deleteOwnerPerson", method = RequestMethod.POST)
    public ResponseEntity deleteOwnerPerson(@RequestBody String str) {

        try {
            courseService.deleteOwnerPersonForCourse(getUser(str), getCourse(str));
            return new ResponseEntity(HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity(e, HttpStatus.BAD_REQUEST);
        }

    }



    @CrossOrigin(origins = "http://localhost:4200")
    @RequestMapping(value = "/courses/addCourse", method = RequestMethod.POST)
    public ResponseEntity addCouse(@RequestBody String courseJson) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            JsonNode node = null;
            node = mapper.readTree(courseJson);
            CourseDTO courseDTO = mapper.convertValue(node.get("course"), CourseDTO.class);
            List<Integer> ownersIds = mapper.convertValue(node.get("ownersIds"), List.class);
            List<Integer> contactPersonsId = mapper.convertValue(node.get("contactPersonsId"), List.class);

            return new ResponseEntity(courseService.addCourse(courseDTO, ownersIds, contactPersonsId), HttpStatus.OK);
        } catch (InvalidDataException e) {
            return new ResponseEntity(e, HttpStatus.BAD_REQUEST);
        } catch (IOException e) {
            return new ResponseEntity(e, HttpStatus.BAD_REQUEST);
        }
    }




    @CrossOrigin(origins = "http://localhost:4200")
    @RequestMapping(value = "/user/courses", method = RequestMethod.GET)
    public ResponseEntity<List<CourseDTO>> getUserCourses(@RequestParam(value = "username") String username) {
        List<CourseDTO> courses = courseService.getCoursesForUser(username);
        return new ResponseEntity<>(courses, HttpStatus.OK);
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


}
