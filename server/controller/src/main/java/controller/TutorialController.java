package controller;

import dto.TutorialDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import service.TutorialService;

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
}
