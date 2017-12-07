package controllers;

import entity.Course;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

/**
 * class to be deleted
 */
@Controller
public class TestController {

//    @CrossOrigin(origins = "http://localhost:4200")
//    @RequestMapping(value = "/courses", method = RequestMethod.GET)
//    public ResponseEntity<List<Course>> allArticles() {
//        List<Course> courses = new ArrayList<>();
//        Course c1 = new Course();
//        c1.setId(1);
//        c1.setOverview("first course ");
////        c1.setSubjects(new ArrayList<>("We will present ..");
//
//        Course c2 = new Course();
//        c2.setId(2);
//        c2.setOverview("second course");
////        c2.setSyllabus("In this article ...");
//        courses.add(c2);
//        courses.add(c1);
//        return new ResponseEntity<>(courses, HttpStatus.OK);
//    }
//
//    @CrossOrigin(origins = "http://localhost:4200")
//    @RequestMapping(value = "/course", method = RequestMethod.GET)
//    public ResponseEntity<List<Course>> searchArticlesByOverview(@RequestParam(value = "overview") String overview) {
//        List<Course> courses = new ArrayList<>();
//        Course c1 = new Course();
//        c1.setId(1);
//        c1.setOverview("first course ");
////        c1.setSyllabus("We will present ..");
//
//        Course c2 = new Course();
//        c2.setId(2);
//        c2.setOverview("second course");
////        c2.setSyllabus("In this article ...");
//        courses.add(c2);
//        courses.add(c1);
//        ArrayList<Course> searchCourses = new ArrayList<>();
//        for (int i = 0; i < courses.size(); i++) {
//            if (courses.get(i).getOverview().contains(overview)) {
//                searchCourses.add(courses.get(i));
//            }
//        }
//
//        return new ResponseEntity<>(searchCourses, HttpStatus.OK);
//
//    }
//
//    @CrossOrigin(origins = "http://localhost:4200")
//    @RequestMapping(value = "/detailedCourse", method = RequestMethod.GET)
//    public ResponseEntity<Course> getDetails(@RequestParam(value = "id") String id) {
//        List<Course> courses = new ArrayList<>();
//        Course c1 = new Course();
//        c1.setId(1);
//        c1.setOverview("first course ");
////        c1.setSyllabus("We will present ..");
//
//        Course c2 = new Course();
//        c2.setId(2);
//        c2.setOverview("second course");
////        c2.setSyllabus("In this article ...");
//        courses.add(c2);
//        courses.add(c1);
//
//        for (int i = 0; i < courses.size(); i++) {
//            if (courses.get(i).getIdCourse() == Integer.parseInt(id)) {
//                return new ResponseEntity<Course>(courses.get(i), HttpStatus.OK);
//            }
//        }
//
//        return null;
//
//
//    }
}
