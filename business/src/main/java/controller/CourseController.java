package controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import dto.CourseDTO;
import dto.SubjectDTO;
import dto.UserDTO;
import exception.CourseNotFoundException;
import exception.InvalidDataException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import service.CourseService;
import service.SubjectService;
import service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@Controller
public class CourseController {
    @Autowired
    private CourseService courseService;

    @Autowired
    private UserService userService;

    @Autowired
    private SubjectService subjectService;


    @CrossOrigin(origins = "http://localhost:4200")
    @RequestMapping(value = "/courses", method = RequestMethod.GET)
    public ResponseEntity<List<CourseDTO>> allArticles(HttpServletRequest request) {
        HttpSession session = request.getSession(true);
        SecurityContext sc = (SecurityContext) session.getAttribute("SPRING_SECURITY_CONTEXT");
        return new ResponseEntity<>(courseService.getAllCourses(), HttpStatus.OK);
    }


    @CrossOrigin(origins = "http://localhost:4200")
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

    @CrossOrigin(origins = "http://localhost:4200")
    @RequestMapping(value = "courses/detailedCourse", method = RequestMethod.GET)

    public ResponseEntity getDetails(@RequestParam(value = "id") Integer id) {
        CourseDTO courseById = null;
        try {
            courseById = courseService.getCourseById(id);
        } catch (CourseNotFoundException e) {
            return new ResponseEntity<>(e, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(courseById, HttpStatus.OK);
    }


    @CrossOrigin(origins = "http://localhost:4200")
    @RequestMapping(value = "/courses/enrollUserOnCourse", method = RequestMethod.POST)
    public ResponseEntity enrollToCourse(@RequestParam(value = "idCourse") Integer idCourse, @RequestBody String username) {
        courseService.enrollUserToCourse(username, idCourse);
        return null;
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @RequestMapping(value = "/courses/isEnrolledOnCourse", method = RequestMethod.POST)
    public ResponseEntity<Boolean> isEnrolledOnCourse(@RequestParam(value = "idCourse") Integer idCourse, @RequestBody String username) {
        return new ResponseEntity<Boolean>(courseService.userIsEnrolledOnCourse(username, idCourse), HttpStatus.OK);
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @RequestMapping(value = "/courses/unenrollUserFromCourse", method = RequestMethod.POST)
    public ResponseEntity unenrollFromCourse(@RequestParam(value = "idCourse") Integer idCourse, @RequestBody String username) {
        courseService.unenrollUserToCourse(username, idCourse);
        return null;
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @RequestMapping(value = "/courses/updateCourse", method = RequestMethod.POST)
    public ResponseEntity updateCourse(@RequestBody CourseDTO course) {

        try {
            courseService.updateCourse(course);
            return new ResponseEntity(HttpStatus.OK);
        } catch (InvalidDataException e) {
            return new ResponseEntity(e, HttpStatus.BAD_REQUEST);
        }

    }


    //    @CrossOrigin(origins = "http://localhost:4200")
//    @RequestMapping(value = "/createCourse", method = RequestMethod.POST)
//    public ResponseEntity createCourse() {
//        UserDTO owner1 = new UserDTO();
//        owner1.setName("Owner1");
//        owner1.setEmail("owner1@gmail.com");
//        owner1.setUsername("owner1prim");
//        owner1.setPassword("owner1prim");
//        userService.createUser(owner1);
//
//        UserDTO owner2 = new UserDTO();
//        owner2.setName("Owner2");
//        owner2.setEmail("owner2@gmail.com");
//        owner2.setUsername("owner2prim");
//        owner2.setPassword("owner2prim");
//        userService.createUser(owner2);
//
//
//        UserDTO owner3 = new UserDTO();
//        owner3.setName("Owner3");
//        owner3.setEmail("owner3@gmail.com");
//        owner3.setUsername("owner3prim");
//        owner3.setPassword("owner3prim");
//        userService.createUser(owner3);
//
//
//        List<UserDTO> owners = new ArrayList<>();
//        owners.add(owner1);
//        owners.add(owner2);
//        owners.add(owner3);
//
//
//        MaterialDTO material1 = new MaterialDTO();
//        material1.setMaterialType(MaterialType.LINK);
//        material1.setLink("https://www.tutorialspoint.com/java8/java8_pdf_version.htm");
//        materialService.createMaterial(material1);
//
//
//        MaterialDTO material2 = new MaterialDTO();
//        material2.setMaterialType(MaterialType.LINK);
//        material2.setLink("chrome-extension://oemmndcbldboiebfnladdacbdfmadadm/http://www.oracle.com/technetwork/java/newtojava/java8book-2172125.pdf");
//        materialService.createMaterial(material2);
//
//
//        List<MaterialDTO> materials = new ArrayList<>();
//        materials.add(material1);
//        materials.add(material2);
//
//
//        SubjectDTO day1 = new SubjectDTO();
//        day1.setMaterials(materials);
//        subjectService.createSubject(day1);
//
//        SubjectDTO day2 = new SubjectDTO();
//        day2.setMaterials(materials);
//        subjectService.createSubject(day2);
//
//        SubjectDTO day3 = new SubjectDTO();
//        day3.setMaterials(materials);
//        subjectService.createSubject(day3);
//
//
//        SubjectDTO day4 = new SubjectDTO();
//        day4.setMaterials(materials);
//        subjectService.createSubject(day4);
//
//
//
//        List<SubjectDTO> subjects = new ArrayList<>();
//
//        subjects.add(day1);
//        subjects.add(day2);
//        subjects.add(day3);
//        subjects.add(day4);
//
//        CourseDTO courseDTO = new CourseDTO();
//        courseDTO.setOverview("acesta este u curs special");
//        courseDTO.setSubjects(subjects);
//        courseDTO.setOwners(owners);
//        courseDTO.setContactPersons(owners);
//
//        courseService.createCourse(courseDTO);
//        return null;
//    }


    @CrossOrigin(origins = "http://localhost:4200")
    @RequestMapping(value = "courses/subject", method = RequestMethod.GET)

    public ResponseEntity getDetails(@RequestParam(value = "id") Integer id, @RequestParam(value = "idSubject") Integer idSubject) {

        SubjectDTO subject = subjectService.findSubjectOfCourse(id, idSubject);
        if (subject != null) {
            return new ResponseEntity(subject, HttpStatus.OK);
        }
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }


    @CrossOrigin(origins = "http://localhost:4200")
    @RequestMapping(value = "/deleteContactPerson", method = RequestMethod.POST)
    public ResponseEntity deleteContactPerson(@RequestBody String str) {


        try {
            courseService.deleteContactPersonForCourse(getUser(str),getCourse(str));
            return new ResponseEntity(HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity(e, HttpStatus.BAD_REQUEST);
        }

    }


    @CrossOrigin(origins = "http://localhost:4200")
    @RequestMapping(value = "/deleteOwnerPerson", method = RequestMethod.POST)
    public ResponseEntity deleteOwnerPerson(@RequestBody String str)  {

        try {
            courseService.deleteOwnerPersonForCourse(getUser(str),getCourse(str));
            return new ResponseEntity(HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity(e, HttpStatus.BAD_REQUEST);
        }

    }


    @CrossOrigin(origins = "http://localhost:4200")
    @RequestMapping(value = "/deleteCourseSubject", method = RequestMethod.POST)
    public ResponseEntity deleteCourseSubject(@RequestBody String str) {

        try {
            courseService.deleteSubjectFromCourse(getCourse(str),getSubject(str));
            return new ResponseEntity(HttpStatus.OK);

        } catch (IOException e) {
          return new ResponseEntity(e,HttpStatus.BAD_REQUEST );
        }
    }


    @CrossOrigin(origins = "http://localhost:4200")
    @RequestMapping(value = "/addContactPerson", method = RequestMethod.POST)
    public ResponseEntity addCourseContactPerson(@RequestBody String str) {
        ObjectMapper mapper = new ObjectMapper();

        try {
            JsonNode  node = mapper.readTree(str);
            String email=mapper.convertValue(node.get("email"), String.class);

            return new ResponseEntity(courseService.addContactPerson(email,getCourse(str)),HttpStatus.OK);

        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity(e, HttpStatus.BAD_REQUEST);
        }


    }
    @CrossOrigin(origins = "http://localhost:4200")
    @RequestMapping(value = "/addOwnerPerson", method = RequestMethod.POST)
    public ResponseEntity addOnerContactPerson(@RequestBody String str) {
        ObjectMapper mapper = new ObjectMapper();

        try {
            JsonNode  node = mapper.readTree(str);
            String email=mapper.convertValue(node.get("email"), String.class);

            return new ResponseEntity(courseService.addOnerPerson(email,getCourse(str)),HttpStatus.OK);

        } catch (IOException e) {
            e.printStackTrace();
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
