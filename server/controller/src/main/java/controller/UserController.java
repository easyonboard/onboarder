package controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import dto.CheckListDto;
import dto.LeaveCheckListDto;
import dto.UserDto;
import entity.Department;
import entity.enums.RoleType;
import exception.types.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import service.UserService;

import java.io.IOException;
import java.util.List;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @CrossOrigin(origins = "http://localhost:4200")
    @RequestMapping(value = "users/addUser", method = RequestMethod.POST)
    public ResponseEntity addUser(@RequestBody String userJson) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            JsonNode node = mapper.readTree(userJson);
            UserDto userDto = mapper.convertValue(node.get("user"), UserDto.class);
            userService.addUser(userDto);
        } catch (InvalidDataException exception) {
            return new ResponseEntity(exception, HttpStatus.BAD_REQUEST);
        } catch (JsonProcessingException e) {
            return new ResponseEntity(e, HttpStatus.BAD_REQUEST);
        } catch (IOException e) {
            return new ResponseEntity(e, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @CrossOrigin(origins = "http://localhost:4200")
    @RequestMapping(value = "users/updateUser", method = RequestMethod.POST)
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
    @RequestMapping(value = "users/allUsers", method = RequestMethod.GET)
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
    @RequestMapping(value = "users/newUsers", method = RequestMethod.GET)
    public ResponseEntity<List<UserDto>> getAllNewUsers() {
        List<UserDto> newUsers;
        try {
            newUsers = userService.getAllNewUsers();
        } catch (EntityNotFoundException e) {
            return new ResponseEntity(e, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity(newUsers, HttpStatus.OK);
    }



    @CrossOrigin(origins = "http://localhost:4200")
    @RequestMapping(value = "users/checkList", method = RequestMethod.POST)
    public ResponseEntity getCheckList(@RequestBody UserDto user) {
        try {
            return new ResponseEntity(userService.getCheckList(user), HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity(e, HttpStatus.NOT_FOUND);
        }
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @RequestMapping(value = "users/saveCheckList", method = RequestMethod.POST)
    public ResponseEntity saveCheckList(@RequestBody String userAndChecklist) {
        try {
            userService.saveCheckListForUser(getUser(userAndChecklist), getCheckListMap(userAndChecklist));
            return new ResponseEntity(HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity(e, HttpStatus.NOT_FOUND);
        } catch (DatabaseException e) {
            return new ResponseEntity(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @RequestMapping(value = "users/department", method = RequestMethod.GET)
    public ResponseEntity<List<UserDto>> getUsersInDepartmentForUser(
            @RequestParam(value = "msgMail") String msgMail) {

        List<UserDto> users = null;
        try {
            users = userService.getUsersInDepartmentForUser(msgMail);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity(e, HttpStatus.NOT_FOUND);
        } catch (FieldNotFoundException e) {
            return new ResponseEntity(e, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @RequestMapping(value = "users/removeUser", method = RequestMethod.POST)
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
    @RequestMapping(value = "users/isMailSent", method = RequestMethod.POST)
    public ResponseEntity getStatusMailForUser(@RequestBody String username) {

        try {
            return new ResponseEntity<>(userService.getStatusMailForUser(username), HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity(e, HttpStatus.NOT_FOUND);
        }
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @RequestMapping(value = "users/leaveCheckList", method = RequestMethod.POST)
    public ResponseEntity getLeaveCheckList(@RequestBody String username) {

        try {
            return new ResponseEntity<>(userService.getLeaveCheckListForUser(username), HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.OK);
        }
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @RequestMapping(value = "users/saveLeaveCheckList", method = RequestMethod.POST)
    public ResponseEntity saveLeaveCheckList(@RequestBody LeaveCheckListDto leaveCheckList) {

        try {
            return new ResponseEntity<>(userService.saveLeaveCheckList(leaveCheckList), HttpStatus.OK);
        } catch (DatabaseException e) {
            return new ResponseEntity(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @RequestMapping(value = "users/checkUnicity", method = RequestMethod.POST)
    public ResponseEntity checkUserUnicity(@RequestBody String uniqueJson) throws IOException {

        ObjectMapper mapper = new ObjectMapper();
        JsonNode node = mapper.readTree(uniqueJson);

        String username = mapper.convertValue(node.get("username"), String.class);
        String msgMail = mapper.convertValue(node.get("msgMail"), String.class);

        try {
            return new ResponseEntity<>(userService.checkUnicity(username, msgMail), HttpStatus.OK);
        } catch (DuplicatedDataException e) {
            return new ResponseEntity(e, HttpStatus.BAD_REQUEST);
        }
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @RequestMapping(value = "/username", method = RequestMethod.GET)
    public ResponseEntity<UserDto> getUserByUsername(@RequestParam(value = "username") String username) {

        UserDto userDto = null;
        try {
            userDto = userService.findUserByUsername(username);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity(e, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @RequestMapping(value = "user", method = RequestMethod.GET)
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
    @RequestMapping(value = "departments", method = RequestMethod.GET)
    public ResponseEntity<List<Department>> getUserByName() {
        return new ResponseEntity(userService.getAllDepartments(), HttpStatus.OK);
    }

    private String getUser(String str) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode node = mapper.readTree(str);
        return mapper.convertValue(node.get("user"), String.class);
    }

    private CheckListDto getCheckListMap(String str) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode node = mapper.readTree(str);
        return mapper.convertValue(node.get("check"), CheckListDto.class);
    }

}
