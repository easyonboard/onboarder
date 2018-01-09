package controller;

import dto.UserDTO;
import exception.InvalidDataException;
import exception.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import service.UserService;
import utilityService.CustomAuthenticationProvider;
import utilityService.UserLoginService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserLoginService userLoginService;

    @Autowired
    private CustomAuthenticationProvider authentificationProvider;

    @CrossOrigin(origins = "http://localhost:4200")
    @RequestMapping(value = "/auth", method = RequestMethod.POST)
    public ResponseEntity<UserDTO> login(@RequestBody UserDTO user, HttpServletRequest request) throws UserNotFoundException {


        UserDTO userLogged = userService.findUserByUsername(user.getUsername());
        String password = userService.encrypt(user.getPassword());
        String username = user.getUsername();
//
//        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, password);
//        token.setDetails(new WebAuthenticationDetails(request));
//        Authentication authentication = this.authentificationProvider.authenticate(token);
//        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
//        HttpSession session=attr.getRequest().getSession(true);
//        SecurityContext securityContext = SecurityContextHolder.getContext();
//        session.setAttribute("SPRING_SECURITY_CONTEXT", token);
        return new ResponseEntity<>(userLogged, HttpStatus.OK);

    }

    @CrossOrigin(origins = "http://localhost:4200")
    @RequestMapping(value = "/user/addUser", method = RequestMethod.POST)
    public ResponseEntity addUser(@RequestBody UserDTO user) throws InvalidDataException {

        try {
            userService.addUser(user);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (InvalidDataException exception) {
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


}