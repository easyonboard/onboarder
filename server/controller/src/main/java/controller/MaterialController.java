package controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dto.MaterialDTO;
import exception.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import service.MaterialService;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class MaterialController {

    @Autowired
    private MaterialService materialService;


    @CrossOrigin(origins = "http://localhost:4200")
    @RequestMapping(value = "/createMaterial", method = RequestMethod.POST)
    public ResponseEntity addMaterial(@RequestParam(name = "material") String mat, @RequestParam(name = "file") Optional<MultipartFile> file, @RequestParam(name = "idSubject") String idSubject) throws IOException {
        final GsonBuilder gsonBuilder = new GsonBuilder();
        final Gson gson = gsonBuilder.create();
        MaterialDTO material = gson.fromJson(mat, MaterialDTO.class);
        if (file.isPresent()) {
            material.setFileMaterial(file.get().getBytes());
        }
        materialService.createMaterial(material, Integer.parseInt(idSubject));
        return null;
    }


    @CrossOrigin(origins = "http://localhost:4200")
    @RequestMapping(value = "courses/material", method = RequestMethod.GET, produces = "application/pdf")
    public @ResponseBody
    byte[] getMaterialById(@RequestParam(value = "id") Integer id, HttpServletResponse response) {
        MaterialDTO materialDTO = materialService.getMaterialById(id);
        response.setHeader("Content-Disposition", "inline; filename=" + materialDTO.getTitle());
        response.setContentType("application/pdf");
        return materialDTO.getFileMaterial();
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @RequestMapping(value = "/materials", method = RequestMethod.GET)
    public @ResponseBody
    List<MaterialDTO> getMaterialBySubject(@RequestParam(value = "idSubject") Integer id, HttpServletResponse response) {
        List<MaterialDTO> materials = materialService.getMaterialBySubjectId(id);
        return materials;
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @RequestMapping(value = "material/uploadedMaterial", method = RequestMethod.GET)
    public ResponseEntity allMaterialsFromUser(@RequestParam(value = "username") String username) throws UserNotFoundException {
        List<MaterialDTO> materials = materialService.materialUploadedByUser(username);
        materials.stream().forEach(m -> {
            m.setFileMaterial(null);
            m.setContainedBySubjects(null);
        });
        return new ResponseEntity<>(materials, HttpStatus.OK);
    }
}
