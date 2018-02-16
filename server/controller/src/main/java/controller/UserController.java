package controller;

import dto.UserDTO;
import exception.InvalidDataException;
import exception.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import service.UserService;

import java.util.List;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    private static final String CROSS_ORIGIN_STRING = "http://172.20.192.1:4200";


    @CrossOrigin(origins = CROSS_ORIGIN_STRING)
    @RequestMapping(value = "/auth", method = RequestMethod.POST)
    public ResponseEntity login(@RequestBody UserDTO user) {

        try{
        UserDTO userLogged = userService.findUserByUsername(user.getUsername());
        String password = userService.encrypt(user.getPassword());
        if(userLogged.getPassword().equals(password))
            return new ResponseEntity<>(userLogged, HttpStatus.OK);
        return new ResponseEntity(HttpStatus.FORBIDDEN);}
        catch (UserNotFoundException exception){
            return new ResponseEntity(exception, HttpStatus.FORBIDDEN);
        }

    }

    @CrossOrigin(origins =CROSS_ORIGIN_STRING)
    @RequestMapping(value = "/user/addUser", method = RequestMethod.POST)
    public ResponseEntity addUser(@RequestBody UserDTO user) {

        try {
            userService.addUser(user);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (InvalidDataException exception) {
            return new ResponseEntity<>(exception, HttpStatus.BAD_REQUEST);
        }
    }

    @CrossOrigin(origins = CROSS_ORIGIN_STRING)
    @RequestMapping(value = "/user/updateUser", method = RequestMethod.POST)
    public ResponseEntity updateUser(@RequestBody UserDTO user) {
        try {
            userService.updateUser(user);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (InvalidDataException exception) {
            return new ResponseEntity<>(exception, HttpStatus.BAD_REQUEST);
        }
    }



    @CrossOrigin(origins = CROSS_ORIGIN_STRING)
    @RequestMapping(value = "/userEmails", method = RequestMethod.GET)
    public ResponseEntity<List<String>> searchArticlesByOverview(@RequestParam(value = "email") String email) {
        List<String> searchEmail = userService.getUsersEmails(email);
        return new ResponseEntity<>(searchEmail, HttpStatus.OK);
    }

    @CrossOrigin(origins = CROSS_ORIGIN_STRING)
    @RequestMapping(value = "/allUsers", method = RequestMethod.GET)
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<UserDTO> users = userService.getAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }
}