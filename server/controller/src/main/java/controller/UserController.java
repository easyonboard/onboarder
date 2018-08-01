package controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import dto.*;
import entity.enums.RoleType;
import exception.types.DatabaseException;
import exception.types.FieldNotFoundException;
import exception.types.InvalidDataException;
import exception.types.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import service.CheckListService;
import service.RoleService;
import service.UserInformationService;
import service.UserService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private CheckListService checkListService;

    @Autowired
    private UserInformationService userInformationService;

    @CrossOrigin(origins = "http://localhost:4200")
    @RequestMapping(value = "/auth", method = RequestMethod.POST)
    public ResponseEntity login(@RequestBody UserDto user) {

        try {
            UserDto userLogged = userService.findUserByUsername(user.getUsername());
            String password = userService.encrypt(user.getPassword());
            if (userLogged.getPassword().equals(password))
                return new ResponseEntity<>(userLogged, HttpStatus.OK);
            return new ResponseEntity(HttpStatus.FORBIDDEN);
        } catch (EntityNotFoundException exception) {
            return new ResponseEntity(exception, HttpStatus.FORBIDDEN);
        }
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @RequestMapping(value = "/user/addUser", method = RequestMethod.POST)
    public ResponseEntity addUser(@RequestBody String userJson) {

        ObjectMapper mapper = new ObjectMapper();

        try {
            JsonNode node = mapper.readTree(userJson);
            UserDto userDto = mapper.convertValue(node.get("user"), UserDto.class);
            RoleType role = mapper.convertValue(node.get("role"), RoleType.class);
            UserInformationDto userInformationDto = mapper.convertValue(node.get("userInfo"), UserInformationDto.class);

            RoleDto roleDto = null;
            try {
                roleDto = roleService.findRoleByType(role);
            } catch (FieldNotFoundException e) {
                return new ResponseEntity<>(e, HttpStatus.NOT_FOUND);
            }
            userDto.setRole(roleDto);

            userService.addUser(userDto, userInformationDto);
        } catch (InvalidDataException exception) {
            return new ResponseEntity(exception, HttpStatus.BAD_REQUEST);
        } catch (JsonProcessingException e) {
            return new ResponseEntity(e, HttpStatus.BAD_REQUEST);
        } catch (IOException e) {
            return new ResponseEntity(e, HttpStatus.BAD_REQUEST);
        } catch (DatabaseException e) {
            return new ResponseEntity(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @RequestMapping(value = "/user/updateUser", method = RequestMethod.POST)
    public ResponseEntity updateUser(@RequestBody UserDto user) {

        try {
            userService.updateUser(user);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (InvalidDataException exception) {
            return new ResponseEntity<>(exception, HttpStatus.BAD_REQUEST);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(e, HttpStatus.NOT_FOUND);
        } catch (DatabaseException e) {
            return new ResponseEntity<>(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @RequestMapping(value = "/user/updateUserPassword", method = RequestMethod.POST)
    public ResponseEntity updateUserPassword(@RequestBody UserDto user) {

        try {
            userService.updateUserPassword(user.getUsername(), user.getPassword());
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity(e, HttpStatus.NOT_FOUND);
        } catch (DatabaseException e) {
            return new ResponseEntity(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @RequestMapping(value = "/allUsers", method = RequestMethod.GET)
    public ResponseEntity<List<UserDto>> getAllUsers() {

        List<UserDto> users = null;
        try {
            users = userService.getAllUsers();
        } catch (EntityNotFoundException e) {
            return new ResponseEntity(e, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @RequestMapping(value = "/newUsers", method = RequestMethod.GET)
    public ResponseEntity<List<UserInformationDto>> getAllNewUsers() {

        List<UserInformationDto> newUsers = new ArrayList<>();
        try {
            newUsers = userService.getAllNewUsers();
        } catch (EntityNotFoundException e) {
            return new ResponseEntity(e, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity(newUsers, HttpStatus.OK);
    }

    /**
     * Method used for adding/updating the user information, represented by the info the user needs to know
     * for his first day.
     *
     * @return HTTP STATUS OK for successfully adding the info,
     * or HTTP STATUS BAD REQUEST for exception
     */
    @CrossOrigin(origins = "http://localhost:4200")
    @RequestMapping(value = "/user/updateUserInfo", method = RequestMethod.POST)
    public ResponseEntity updateUserInformation(@RequestBody UserInformationDto userInformationDto) {

        try {
            userInformationService.updateUserInfo(userInformationDto);
            return new ResponseEntity(HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(e, HttpStatus.BAD_REQUEST);
        }
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public ResponseEntity<List<UserDto>> getUserByName(@RequestParam(value = "name") String name) {

        List<UserDto> users = null;
        try {
            users = userService.searchByName(name);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity(e, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @RequestMapping(value = "/checkList", method = RequestMethod.POST)
    public ResponseEntity getCheckList(@RequestBody UserDto user) {

        try {
            return new ResponseEntity(userService.getCheckList(user), HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity(e, HttpStatus.NOT_FOUND);
        }
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @RequestMapping(value = "/saveCheckList", method = RequestMethod.POST)
    public ResponseEntity saveCheckList(@RequestBody String username) {

        try {
            userService.saveCheckListForUser(getUser(username), getCheckListMsp(username));
            return new ResponseEntity(HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity(e, HttpStatus.NOT_FOUND);
        } catch (DatabaseException e) {
            return new ResponseEntity(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private String getUser(String str) throws IOException {

        ObjectMapper mapper = new ObjectMapper();
        JsonNode node = mapper.readTree(str);
        return mapper.convertValue(node.get("user"), String.class);
    }

    private CheckListDto getCheckListMsp(String str) throws IOException {

        ObjectMapper mapper = new ObjectMapper();
        JsonNode node = mapper.readTree(str);
        return mapper.convertValue(node.get("check"), CheckListDto.class);
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @RequestMapping(value = "user/department", method = RequestMethod.GET)
    public ResponseEntity<List<UserDto>> getUsersInDepartmentForUser(
            @RequestParam(value = "username") String username) {

        List<UserDto> users = null;
        try {
            users = userService.getUsersInDepartmentForUser(username);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity(e, HttpStatus.NOT_FOUND);
        } catch (FieldNotFoundException e) {
            return new ResponseEntity(e, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @RequestMapping(value = "user/removeUser", method = RequestMethod.POST)
    public ResponseEntity removeUser(@RequestBody String username) {

        try {
            return new ResponseEntity<>(userService.deleteUser(username), HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        } catch (DatabaseException e) {
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @RequestMapping(value = "getUserInformation", method = RequestMethod.POST)
    public ResponseEntity getUserInformationForUser(@RequestBody String username) {

        try {
            return new ResponseEntity<>(userService.getUserInformationForUser(username), HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity(e, HttpStatus.NOT_FOUND);
        }
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @RequestMapping(value = "getAllMsgMails", method = RequestMethod.GET)
    public ResponseEntity<List<String>> getAllUsersNameAndEmail() {

        try {
            return new ResponseEntity<>(userService.getAllMsgMails(), HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity(e, HttpStatus.NOT_FOUND);
        }
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @RequestMapping(value = "isMailSent", method = RequestMethod.POST)
    public ResponseEntity getStatusMailForUser(@RequestBody String username) {

        try {
            return new ResponseEntity<>(userService.getStatusMailForUser(username), HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity(e, HttpStatus.NOT_FOUND);
        }
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @RequestMapping(value = "leaveCheckList", method = RequestMethod.POST)
    public ResponseEntity getLeaveCheckList(@RequestBody String username) {

        try {
            return new ResponseEntity<>(userService.getLeaveCheckListForUser(username), HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.OK);
        }
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @RequestMapping(value = "saveLeaveCheckList", method = RequestMethod.POST)
    public ResponseEntity saveLeaveCheckList(@RequestBody LeaveCheckListDto leaveCheckList) {

        try {
            return new ResponseEntity<>(userService.saveLeaveCheckList(leaveCheckList), HttpStatus.OK);
        } catch (DatabaseException e) {
            return new ResponseEntity(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @RequestMapping(value = "user/checkUnicity", method = RequestMethod.POST)
    public ResponseEntity checkUserUnicity(@RequestBody String uniqueJson) throws IOException {

        ObjectMapper mapper = new ObjectMapper();
        JsonNode node = mapper.readTree(uniqueJson);

        String username = mapper.convertValue(node.get("username"), String.class);
        String msgMail = mapper.convertValue(node.get("msgMail"), String.class);

        return new ResponseEntity<>(userService.checkUnicity(username, msgMail), HttpStatus.OK);
    }
}
