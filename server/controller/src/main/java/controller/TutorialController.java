package controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sun.media.sound.InvalidDataException;
import dto.TutorialDTO;

import dto.TutorialMaterialDTO;
import entity.TutorialMaterial;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import service.TutorialService;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Controller
public class TutorialController {

    @Autowired
    private TutorialService tutorialService;

    @CrossOrigin(origins = "http://localhost:4200")
    @RequestMapping(value = "/tutorials", method = RequestMethod.GET)
    public ResponseEntity<List<TutorialDTO>> allTutorials(@RequestParam(value = "keyword", required = false) String keyword) {
        if (keyword != null && keyword.length() > 0) {
            return new ResponseEntity<>(tutorialService.filterByKeyword(keyword), HttpStatus.OK);
        }
        return new ResponseEntity<>(tutorialService.getAllTutorials(), HttpStatus.OK);
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @RequestMapping(value = "/tutorials/addTutorial", method = RequestMethod.POST)
    public ResponseEntity addTutorial(@RequestBody String courseJson) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            JsonNode node = null;
            node = mapper.readTree(courseJson);
            TutorialDTO tutorialDTO = mapper.convertValue(node.get("tutorial"), TutorialDTO.class);
            List<String> contactPersons = mapper.convertValue(node.get("contactPersons"), List.class);

            return new ResponseEntity(tutorialService.addTutorial(tutorialDTO, null, contactPersons), HttpStatus.OK);
        } catch (InvalidDataException e) {
            return new ResponseEntity(e, HttpStatus.BAD_REQUEST);
        } catch (IOException e) {
            return new ResponseEntity(e, HttpStatus.BAD_REQUEST);
        }
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @RequestMapping(value = "/tutorials/addTutorialMaterial", method = RequestMethod.POST)
//    public ResponseEntity addTutorialMaterial(@RequestBody String courseJson) {
//        ObjectMapper mapper = new ObjectMapper();
//        try {
//            JsonNode node = null;
//            node = mapper.readTree(courseJson);
//            TutorialMaterialDTO tutorialMaterialDTO = mapper.convertValue(node.get("material"), TutorialMaterialDTO .class);
//            //tutorialMaterialDTO.setFileMaterial(mapper.convertValue(node.get("material"), TutorialMaterialDTO .class));
//            Integer idTutorial = mapper.convertValue(node.get("idTutorial"), Integer.class);
//            TutorialDTO tutorialDTO = tutorialService.getTutorialById(idTutorial);
//
//            tutorialMaterialDTO.setTutorial(tutorialDTO);
//
//            return new ResponseEntity(tutorialService.addTutorialMaterial(tutorialMaterialDTO), HttpStatus.OK);
//        } catch (InvalidDataException e) {
//            return new ResponseEntity(e, HttpStatus.BAD_REQUEST);
//        } catch (IOException e) {
//            return new ResponseEntity(e, HttpStatus.BAD_REQUEST);
//        }
//    }

    public ResponseEntity addTutorialMaterial(@RequestParam(name = "material") String mat,
                                              @RequestParam(name = "file") Optional<MultipartFile> file,
                                              @RequestParam(name = "idTutorial") Integer idTutorial) throws IOException {
        final GsonBuilder gsonBuilder = new GsonBuilder();
        final Gson gson = gsonBuilder.create();
        TutorialMaterialDTO material = gson.fromJson(mat, TutorialMaterialDTO.class);

        TutorialDTO tutorial = tutorialService.findTutorialById(idTutorial);
        material.setTutorial(tutorial);
        if (file.isPresent()) {
            material.setFileMaterial(file.get().getBytes());
        }
        tutorialService.addTutorialMaterial(material);


        return null;
    }

    @CrossOrigin(origins="http://localhost:4200")
    @RequestMapping(value = "materialTutorial", method = RequestMethod.GET, produces = "application/pdf")
    public @ResponseBody
    byte[] getMaterialById(@RequestParam(value = "id") Integer id, HttpServletResponse response) {
        TutorialMaterialDTO materialDTO = tutorialService.getMaterialById(id);
        response.setHeader("Content-Disposition", "inline; filename=" + materialDTO.getTitle());
        response.setContentType("application/pdf");
        return materialDTO.getFileMaterial();
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @RequestMapping(value = "/tutorials/materialsForTutorial", method = RequestMethod.GET)
    public ResponseEntity<List<TutorialMaterialDTO>> allTutorials(@RequestParam(value = "id") Integer idTutorial) {
        return new ResponseEntity<>(tutorialService.getAllMaterialsForTutorial(idTutorial), HttpStatus.OK);
    }

}