package controller;

import dto.CourseDTO;
import dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import service.CourseService;
import service.UserService;

import java.util.ArrayList;
import java.util.List;

@Controller
public class CourseController {
    @Autowired
    private CourseService courseService;


    @CrossOrigin(origins = "http://localhost:4200")
    @RequestMapping(value = "/courses", method = RequestMethod.GET)
    public ResponseEntity<List<CourseDTO>> allArticles() {
        return new ResponseEntity<>(courseService.getAllCourses(), HttpStatus.OK);
    }


//    TODO: perfomance analysis
    @CrossOrigin(origins = "http://localhost:4200")
    @RequestMapping(value = "/course", method = RequestMethod.GET)
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

    @CrossOrigin(origins = "http://localhost:4200")
    @RequestMapping(value = "/detailedCourse", method = RequestMethod.GET)
    public ResponseEntity<CourseDTO> getDetails(@RequestParam(value = "id") Integer id) {
        return new ResponseEntity<>(courseService.getCourseById(id), HttpStatus.OK);
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @RequestMapping(value = "/enrollUserOnCourse", method = RequestMethod.POST)
    public ResponseEntity enrollToCourse(@RequestParam(value = "idCourse") Integer idCourse, @RequestBody String username) {
        courseService.enrollUserToCourse(username, idCourse);
        return null;
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @RequestMapping(value = "/isEnrolledOnCourse", method = RequestMethod.POST)
    public ResponseEntity<Boolean> isEnrolledOnCourse(@RequestParam(value = "idCourse") Integer idCourse, @RequestBody String username) {
        return new ResponseEntity<Boolean>(courseService.userIsEnrolledOnCourse(username, idCourse), HttpStatus.OK);
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @RequestMapping(value = "/unenrollUserFromCourse", method = RequestMethod.POST)
    public ResponseEntity unenrollFromCourse(@RequestParam(value = "idCourse") Integer idCourse, @RequestBody String username) {
        courseService.unenrollUserToCourse(username, idCourse);
        return null;
    }


}
