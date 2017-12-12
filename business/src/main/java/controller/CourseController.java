package controller;

import dto.CourseDTO;
import dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
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
    @RequestMapping(value = "/enrollUserOnCourse", method = RequestMethod.GET)
    public ResponseEntity enrollToCourse(@RequestParam(value = "idCourse") Integer idCourse) {
//        UserDTO user = (UserDTO)SecurityContextHolder.getContext().getAuthentication();
        UserDTO user = new UserDTO();
        user.setUsername("ionelMarian");
//        userService.createUser(user);
        courseService.enrollUserToCourse(user, idCourse);
        return null;

    }

    @CrossOrigin(origins = "http://localhost:4200")
    @RequestMapping(value = "/isEnrolledOnCourse", method = RequestMethod.GET)
    public ResponseEntity<Boolean> isEnrolledOnCourse(@RequestParam(value = "idCourse") Integer idCourse) {
//        UserDTO user = (UserDTO)SecurityContextHolder.getContext().getAuthentication();
        UserDTO user = new UserDTO();
        user.setUsername("ionelMarian");
//        userService.createUser(user);
        return new ResponseEntity<Boolean>(courseService.userIsEnrolledOnCourse(user, idCourse), HttpStatus.OK);

    }

}
