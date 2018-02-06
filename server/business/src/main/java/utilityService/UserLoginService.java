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

@Service
public class UserLoginService implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws  UsernameNotFoundException{

        UserDTO user= null;
        try {
            user = userService.findUserByUsername(username);
        } catch (UserNotFoundException e) {
        e.printStackTrace();
    }
        return new User(user.getUsername(),user.getPassword(), AuthorityUtils.createAuthorityList(user.getRole().getRole().name()));

    }



}
