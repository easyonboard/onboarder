package controller;

import dto.UserDTO;
import dto.UserInformationDTO;
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

//    @Autowired
//    private UserInformationService userInformationService;


    @CrossOrigin(origins = "http://localhost:4200")
    @RequestMapping(value = "/auth", method = RequestMethod.POST)
    public ResponseEntity login(@RequestBody UserDTO user) {

        try {
            UserDTO userLogged = userService.findUserByUsername(user.getUsername());
            String password = userService.encrypt(user.getPassword());
            if (userLogged.getPassword().equals(password))
                return new ResponseEntity<>(userLogged, HttpStatus.OK);
            return new ResponseEntity(HttpStatus.FORBIDDEN);
        } catch (UserNotFoundException exception) {
            return new ResponseEntity(exception, HttpStatus.FORBIDDEN);
        }
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @RequestMapping(value = "/user/addUser", method = RequestMethod.POST)
    public ResponseEntity addUser(@RequestBody UserDTO user) {
        try {
            UserDTO userDTO = new UserDTO();
            userDTO.setUsername(user.getUsername());
            userDTO.setPassword(user.getPassword());
            userDTO.setEmail(user.getEmail());
            userDTO.setName(user.getName());
            userDTO.setRole(user.getRole());

            userService.addUser(userDTO);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (InvalidDataException exception) {
            return new ResponseEntity<>(exception, HttpStatus.BAD_REQUEST);
        }
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @RequestMapping(value = "/user/addUserInfo", method = RequestMethod.POST)
    public ResponseEntity addUserInfo(@RequestBody UserInformationDTO userInfo) {
        try {
            UserInformationDTO userInfoDTO = new UserInformationDTO();
            userInfoDTO.setTeam(userInfo.getTeam());
            userInfoDTO.setBuilding(userInfo.getBuilding());
            userInfoDTO.setFloor(userInfo.getFloor());
            userInfoDTO.setBuddyUser(userInfo.getBuddyUser());

            userService.addUserInfo(userInfoDTO);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception exception) {
            return new ResponseEntity<>(exception, HttpStatus.BAD_REQUEST);
        }
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @RequestMapping(value = "/user/updateUser", method = RequestMethod.POST)
    public ResponseEntity updateUser(@RequestBody UserDTO user) {
        try {
            userService.updateUser(user);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (InvalidDataException exception) {
            return new ResponseEntity<>(exception, HttpStatus.BAD_REQUEST);
        }
    }


    @CrossOrigin(origins = "http://localhost:4200")
    @RequestMapping(value = "/allUsers", method = RequestMethod.GET)
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<UserDTO> users = userService.getAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }


    @CrossOrigin(origins = "http://localhost:4200")
    @RequestMapping(value = "/newUsers", method = RequestMethod.GET)
    public ResponseEntity<List<UserInformationDTO>> getAllNewUsers() {
        List<UserInformationDTO> asd = userService.getAllNewUsers();
        return new ResponseEntity(userService.getAllNewUsers(), HttpStatus.OK);
    }
    /**
     * Method used for adding/updating the user information, represented by the info the user needs to know
     * for his first day.
     *
     * @return HTTP STATUS OK for successfully adding the info,
     * or HTTP STATUS BAD REQUEST for exception
     */
//    @CrossOrigin(origins = "http://localhost:4200")
//    @RequestMapping(value = "/info", method = RequestMethod.POST)
//    public ResponseEntity addUserInformation(@RequestBody UserInformationDTO userInformationDTO) {
//        try {
//            userInformationService.addUser(userInformationDTO);
//            return new ResponseEntity(HttpStatus.OK);
//        } catch (Exception e) {
//            return new ResponseEntity<>(e, HttpStatus.BAD_REQUEST);
//        }
//    }
//
//
    @CrossOrigin(origins = "http://localhost:4200")
    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public ResponseEntity<List<UserDTO>> getUserByName(@RequestParam(value = "username") String name) {
           List<UserDTO> users = userService.searchByUsername(name);
            return new ResponseEntity<>(users, HttpStatus.OK);
    }

}