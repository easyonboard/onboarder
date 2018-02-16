package controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dto.CourseDTO;
import dto.SubjectDTO;
import dto.UserDTO;
import exception.CourseNotFoundException;
import exception.InvalidDataException;
import exception.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import service.CourseService;
import service.SubjectService;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

@Controller
public class CourseController {
    @Autowired
    private CourseService courseService;

    @Autowired
    private SubjectService subjectService;

    private static final String CROSS_ORIGIN_STRING = "http://172.20.192.1:4200";


    @CrossOrigin(origins = CROSS_ORIGIN_STRING)
    @RequestMapping(value = "/courses", method = RequestMethod.GET)
    public ResponseEntity<List<CourseDTO>> allArticles(HttpServletRequest request) {
        return new ResponseEntity<>(courseService.getAllCourses(), HttpStatus.OK);
    }


    @CrossOrigin(origins = CROSS_ORIGIN_STRING)
    @RequestMapping(value = "/courses/course", method = RequestMethod.GET)
    public ResponseEntity<List<CourseDTO>> searchArticlesByOverview(@RequestParam(value = "overview") String overview) {

        List<CourseDTO> searchCourses = courseService.searchByOverview(overview);
//        List<CourseDTO> courses = courseService.getAllCourses();
//        for (int i = 0; i < courses.size(); i++) {
//            if (courses.get(i).getOverview().contains(overview)) {
//                searchCourses.add(courses.get(i));
//            }
//        }
        return new ResponseEntity<>(searchCourses, HttpStatus.OK);

    }

    @CrossOrigin(origins = CROSS_ORIGIN_STRING)
    @RequestMapping(value = "courses/detailedCourse", method = RequestMethod.GET)

    public ResponseEntity getDetails(@RequestParam(value = "id") Integer id) {

        try {
            CourseDTO courseById = courseService.getCourseById(id);
            return new ResponseEntity<>(courseById, HttpStatus.OK);
        } catch (CourseNotFoundException e) {
            return new ResponseEntity<>(e, HttpStatus.BAD_REQUEST);
        }

    }


    @CrossOrigin(origins = CROSS_ORIGIN_STRING)
    @RequestMapping(value = "/courses/enrollUserOnCourse", method = RequestMethod.POST)
    public ResponseEntity enrollToCourse(@RequestParam(value = "idCourse") Integer idCourse, @RequestBody String username) throws UserNotFoundException {
        courseService.enrollUserToCourse(username, idCourse);
        return new ResponseEntity(HttpStatus.OK);
    }

    @CrossOrigin(origins = CROSS_ORIGIN_STRING)
    @RequestMapping(value = "/courses/isEnrolledOnCourse", method = RequestMethod.POST)
    public ResponseEntity<Boolean> isEnrolledOnCourse(@RequestParam(value = "idCourse") Integer idCourse, @RequestBody String username) throws UserNotFoundException {
        return new ResponseEntity<Boolean>(courseService.userIsEnrolledOnCourse(username, idCourse), HttpStatus.OK);
    }

    @CrossOrigin(origins = CROSS_ORIGIN_STRING)
    @RequestMapping(value = "/courses/unenrollUserFromCourse", method = RequestMethod.POST)
    public ResponseEntity unenrollFromCourse(@RequestParam(value = "idCourse") Integer idCourse, @RequestBody String username) throws UserNotFoundException {
        courseService.unenrollUserToCourse(username, idCourse);
        return new ResponseEntity(HttpStatus.OK);
    }

    @CrossOrigin(origins = CROSS_ORIGIN_STRING)
    @RequestMapping(value = "/courses/updateCourse", method = RequestMethod.POST)
    public ResponseEntity updateCourse(@RequestBody CourseDTO course) {

        try {
            courseService.updateCourse(course);
            return new ResponseEntity(HttpStatus.OK);
        } catch (InvalidDataException e) {
            return new ResponseEntity(e, HttpStatus.BAD_REQUEST);
        }

    }

    @CrossOrigin(origins = CROSS_ORIGIN_STRING)
    @RequestMapping(value = "courses/subject", method = RequestMethod.GET)
    public ResponseEntity getDetails(@RequestParam(value = "id") Integer id, @RequestParam(value = "idSubject") Integer idSubject) {

        SubjectDTO subject = subjectService.findSubjectOfCourse(id, idSubject);
        if (subject != null) {
            return new ResponseEntity(subject, HttpStatus.OK);
        }
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

    //    public ResponseEntity addMaterial(@RequestParam(name = "material") String mat, @RequestParam(name = "file") MultipartFile file) throws IOException {
    @CrossOrigin(origins = CROSS_ORIGIN_STRING)
    @RequestMapping(value = "/deleteContactPerson", method = RequestMethod.POST)
    public ResponseEntity deleteContactPerson(@RequestBody String str) {

        try {
            courseService.deleteContactPersonForCourse(getUser(str), getCourse(str));
            return new ResponseEntity(HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity(e, HttpStatus.BAD_REQUEST);
        }

    }


    @CrossOrigin(origins = CROSS_ORIGIN_STRING)
    @RequestMapping(value = "/deleteOwnerPerson", method = RequestMethod.POST)
    public ResponseEntity deleteOwnerPerson(@RequestBody String str) {

        try {
            courseService.deleteOwnerPersonForCourse(getUser(str), getCourse(str));
            return new ResponseEntity(HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity(e, HttpStatus.BAD_REQUEST);
        }

    }


    @CrossOrigin(origins = CROSS_ORIGIN_STRING)
    @RequestMapping(value = "/deleteCourseSubject", method = RequestMethod.POST)
    public ResponseEntity deleteCourseSubject(@RequestBody String str) {

        try {
            courseService.deleteSubjectFromCourse(getCourse(str), getSubject(str));
            return new ResponseEntity(HttpStatus.OK);

        } catch (IOException e) {
            return new ResponseEntity(e, HttpStatus.BAD_REQUEST);
        }
    }


    @CrossOrigin(origins = CROSS_ORIGIN_STRING)
    @RequestMapping(value = "/addContactPerson", method = RequestMethod.POST)
    public ResponseEntity addCourseContactPerson(@RequestBody String str) throws UserNotFoundException {
        ObjectMapper mapper = new ObjectMapper();

        try {
            JsonNode node = mapper.readTree(str);
            String email = mapper.convertValue(node.get("email"), String.class);

            return new ResponseEntity(courseService.addContactPerson(email, getCourse(str)), HttpStatus.OK);

        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity(e, HttpStatus.BAD_REQUEST);
        }


    }

    @CrossOrigin(origins =CROSS_ORIGIN_STRING)
    @RequestMapping(value = "/addOwnerPerson", method = RequestMethod.POST)
    public ResponseEntity addOnerContactPerson(@RequestBody String str) throws UserNotFoundException {
        ObjectMapper mapper = new ObjectMapper();

        try {
            JsonNode node = mapper.readTree(str);
            String email = mapper.convertValue(node.get("email"), String.class);

            return new ResponseEntity(courseService.addOwnerPerson(email, getCourse(str)), HttpStatus.OK);

        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity(e, HttpStatus.BAD_REQUEST);
        }


    }

    @CrossOrigin(origins = CROSS_ORIGIN_STRING)
    @RequestMapping(value = "/courses/addCourse", method = RequestMethod.POST)
    public ResponseEntity addCouse(@RequestBody String courseJson) {
//        final GsonBuilder gsonBuilder = new GsonBuilder();
//        final Gson gson = gsonBuilder.create();
//        CourseDTO courseDTO = gson.fromJson(courseJson, CourseDTO.class);
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
//            e.printStackTrace();
            return new ResponseEntity(e, HttpStatus.BAD_REQUEST);
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

    private SubjectDTO getSubject(String str) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode node = mapper.readTree(str);
        return mapper.convertValue(node.get("subject"), SubjectDTO.class);

    }
}
