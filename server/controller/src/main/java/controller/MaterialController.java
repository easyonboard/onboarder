package controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import service.MaterialService;
import service.TutorialService;


@Controller
public class MaterialController {

    @Autowired
    private MaterialService materialService;

    @CrossOrigin(origins = "http://localhost:4200")
    @RequestMapping(value = "/material/delete", method = RequestMethod.GET)
    public ResponseEntity deleteTutorial(@RequestParam(value = "id", required = true) Integer materialId) {
        materialService.deleteTutorial(materialId);
       return new ResponseEntity(HttpStatus.OK);
    }
}
