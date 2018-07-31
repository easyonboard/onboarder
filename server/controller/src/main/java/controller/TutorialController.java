package controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sun.media.sound.InvalidDataException;
import dto.TutorialDto;

import dto.TutorialMaterialDTO;
import exception.types.DatabaseException;
import exception.types.EntityNotFoundException;
import exception.types.NoDataException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import service.TutorialService;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static exception.Constants.PARSING_EXCEPTION;

@RestController
@RequestMapping("/tutorials")
public class TutorialController {


    @Autowired
    private TutorialService tutorialService;

    @CrossOrigin(origins = "http://localhost:4200")
    @RequestMapping(value = "/ping", method = RequestMethod.GET)
    @ResponseBody
    public String pingTutorials() {

        return "Hello form Tutorials! :)";
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<TutorialDto>> allTutorials(
            @RequestParam(value = "keyword", required = false) String keyword) {

        if (keyword != null && keyword.length() > 0) {
            return new ResponseEntity<>(tutorialService.filterByKeyword(keyword), HttpStatus.OK);
        }
        return new ResponseEntity<>(tutorialService.getAllPublicTutorials(), HttpStatus.OK);
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @RequestMapping(value = "/add", consumes = "application/json", method = RequestMethod.POST)
    public ResponseEntity<Object> addTutorial(@RequestBody String tutorialJSON) {

        ObjectMapper mapper = new ObjectMapper();
        try {
            JsonNode node = null;
            node = mapper.readTree(tutorialJSON);
            JsonNode nodeTutorial = node.get("tutorial");
            JsonNode nodeContactPersonMsgMails = node.get("contactPersonMsgMails");
            TutorialDto tutorialDto = mapper.convertValue(nodeTutorial, TutorialDto.class);
            List<String> contactPersonMsgMails = mapper.readValue(nodeContactPersonMsgMails.toString(), new TypeReference<List<String>>() {
            });

            try {
                return new ResponseEntity<>(tutorialService.addTutorial(tutorialDto, contactPersonMsgMails),
                        HttpStatus.OK);
            } catch (EntityNotFoundException | DatabaseException e) {
                return new ResponseEntity<>(e, HttpStatus.BAD_REQUEST);
            }
        } catch (IOException e) {
            return new ResponseEntity<>(PARSING_EXCEPTION, HttpStatus.BAD_REQUEST);
        }
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @RequestMapping(value = "/addTutorialMaterial", method = RequestMethod.POST)
    public ResponseEntity addTutorialMaterial(@RequestParam(name = "material") String mat,
                                              @RequestParam(name = "file") Optional<MultipartFile> file, @RequestParam(
            name = "idTutorial") Integer idTutorial) {

        final GsonBuilder gsonBuilder = new GsonBuilder();
        final Gson gson = gsonBuilder.create();
        TutorialMaterialDTO material = gson.fromJson(mat, TutorialMaterialDTO.class);

        TutorialDto tutorial = null;
        try {
            tutorial = tutorialService.getTutorialById(idTutorial);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(e, HttpStatus.BAD_REQUEST);
        }
        material.setTutorial(tutorial);
        if (file.isPresent()) {
            try {
                material.setFileMaterial(file.get().getBytes());
            } catch (IOException e) {
                return new ResponseEntity<>(PARSING_EXCEPTION, HttpStatus.BAD_REQUEST);
            }
        }
        tutorialService.addTutorialMaterial(material);

        return null;
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @RequestMapping(value = "/materialTutorial", method = RequestMethod.GET, produces = "application/pdf")
    public @ResponseBody
    byte[] getMaterialById(@RequestParam(value = "id") Integer id, HttpServletResponse response) {

        TutorialMaterialDTO materialDTO = tutorialService.getMaterialById(id);
        response.setHeader("Content-Disposition", "inline; filename=" + materialDTO.getTitle());
        response.setContentType("application/pdf");
        return materialDTO.getFileMaterial();
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @RequestMapping(value = "/materialsForTutorial", method = RequestMethod.GET)
    public ResponseEntity<List<TutorialMaterialDTO>> allTutorials(@RequestParam(value = "id") Integer idTutorial) {

        try {
            return new ResponseEntity<>(tutorialService.getAllMaterialsForTutorial(idTutorial), HttpStatus.OK);
        } catch (EntityNotFoundException | NoDataException e) {
            return new ResponseEntity(e, HttpStatus.BAD_REQUEST);
        }
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<TutorialDto> getTutorialById(
            @PathVariable(value = "id", required = true) Integer idTutorial) {

        try {
            return new ResponseEntity<>(tutorialService.getTutorialById(idTutorial), HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity(e, HttpStatus.BAD_REQUEST);
        }
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @RequestMapping(value = "/deleteTutorial", method = RequestMethod.POST)
    public ResponseEntity<List<TutorialDto>> deleteTutorial(@RequestBody TutorialDto tutorial) {

        try {
            return new ResponseEntity<>(tutorialService.deleteTutorial(tutorial), HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity(e, HttpStatus.BAD_REQUEST);
        }
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ResponseEntity<Object> updateTutorial(@RequestBody String tutorialJSON) {

        ObjectMapper mapper = new ObjectMapper();
        try {
            JsonNode node = null;
            node = mapper.readTree(tutorialJSON);
            TutorialDto tutorialDto = mapper.convertValue(node.get("tutorial"), TutorialDto.class);
            List<String> contactPersons = mapper.convertValue(node.get("contactPersons"), List.class);

            return new ResponseEntity<>(tutorialService.updateTutorial(tutorialDto, contactPersons), HttpStatus.OK);
        } catch (InvalidDataException e) {
            return new ResponseEntity<>(e, HttpStatus.BAD_REQUEST);
        } catch (IOException e) {
            return new ResponseEntity<>(PARSING_EXCEPTION, HttpStatus.BAD_REQUEST);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(e, HttpStatus.BAD_REQUEST);
        }
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @RequestMapping(value = "/draft", method = RequestMethod.GET)
    public ResponseEntity<List<TutorialDto>> allDraftTutorialsForUser(
            @RequestParam(value = "idUser", required = false) Integer idUser,
            @RequestParam(value = "keyword", required = false) Optional<String> keyword) {
        try {
            if (keyword.isPresent()) {
                return new ResponseEntity<>(tutorialService.allDraftTutorialsForUserFilterByKeyword(idUser, keyword.get()), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(tutorialService.allDraftTutorialsForUser(idUser), HttpStatus.OK);
            }
        } catch (NoDataException e) {
            return new ResponseEntity(e, HttpStatus.BAD_REQUEST);
        }
    }
}
