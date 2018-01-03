package utilityService;

import dto.UserDTO;
import exception.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import service.UserService;

/**
 * Created by maresb on 12/15/2017.
 */
@Service
public class UserLoginService implements UserDetailsService {

    @Autowired
    private final UserService userService;


    public UserLoginService(UserService userService){
        this.userService=userService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UserDTO user= null;
        try {
            user = userService.findUserByUsername(username);
        } catch (UserNotFoundException e) {
            e.printStackTrace();
        }
        User u=new User(user.getUsername(),user.getPassword(), AuthorityUtils.createAuthorityList(user.getRole().getRole().toString()));
        u.isEnabled();
        return u;
    }
}
