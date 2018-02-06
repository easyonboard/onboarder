package utilityService;

import dto.UserDTO;
import exception.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.stereotype.Component;
import service.UserService;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private UserService userService;


    @Autowired
    private UserLoginService userLoginService;


    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String userName = authentication.getName();
        UserDTO user = null;

        try {
            user = userService.findUserByUsername(userName);
        } catch (UserNotFoundException e) {
            e.printStackTrace();
        }


        UsernamePasswordAuthenticationToken us = new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword(), AuthorityUtils.createAuthorityList(user.getRole().getRole().name()));
        us.setDetails(userLoginService.loadUserByUsername(userName));

        return us;
    }


    @Override
    public boolean supports(Class<?> authentication) {
        return true;
    }

}
