package controller;

import dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import service.UserService;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @CrossOrigin(origins ="http://localhost:4200")
    @RequestMapping(value = "/auth", method = RequestMethod.POST)
    public ResponseEntity<UserDTO> login(@RequestBody UserDTO user){

        UserDTO userLogged=userService.findUserByUsername(user.getUsername());
        if(userLogged!=null &&
        userLogged.getPassword().equals(user.getPassword()))
            return new ResponseEntity<>(userLogged, HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }


}