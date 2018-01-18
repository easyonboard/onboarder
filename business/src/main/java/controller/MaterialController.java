package controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dto.MaterialDTO;
import dto.UserDTO;
import exception.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import service.MaterialService;
import service.UserService;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Controller
public class MaterialController {

    @Autowired
    private MaterialService materialService;

//    @Autowired
//    private UserService userService;

    @CrossOrigin(origins = "http://localhost:4200")
    @RequestMapping(value = "/createMaterial", method = RequestMethod.POST)
    public ResponseEntity addMaterial(@RequestParam(name = "material") String mat, @RequestParam(name = "file") MultipartFile file) throws IOException {
        final GsonBuilder gsonBuilder = new GsonBuilder();
        final Gson gson = gsonBuilder.create();
        MaterialDTO material = gson.fromJson(mat, MaterialDTO.class);
        material.setFileMaterial(file.getBytes());
        materialService.createMaterial(material);
        return null;
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @RequestMapping(value = "courses/material", method = RequestMethod.GET, produces = "application/pdf")
    public @ResponseBody  byte[] getMaterialById(@RequestParam(value = "id") Integer id, HttpServletResponse response) throws IOException {
        MaterialDTO materialDTO = materialService.getMaterialById(id);
        response.setHeader("Content-Disposition", "inline; filename="+materialDTO.getTitle());
        response.setContentType("application/pdf");
        return materialDTO.getFileMaterial();
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @RequestMapping(value = "material/uploadedMaterial", method = RequestMethod.GET)
    public ResponseEntity allMaterialsFromUser(@RequestParam(value = "username") String username) throws UserNotFoundException {
        List<MaterialDTO> materials = materialService.materialUploadedByUser(username);
        materials.stream().forEach(m->{m.setFileMaterial(null);
        m.setContainedBySubjects(null);});
        return new ResponseEntity<>(materials, HttpStatus.OK);
    }
}
