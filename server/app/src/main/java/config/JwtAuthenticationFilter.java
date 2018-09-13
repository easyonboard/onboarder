package config;

import dao.UserRepository;
import entity.User;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.SignatureException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;
import service.AuthUserService;
import sun.plugin.liveconnect.SecurityContextHelper;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private AuthUserService userDetailsService;

    @Autowired
    private UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String header = request.getHeader(HttpHeaders.AUTHORIZATION);
        String username = null;
        String authenticationToken = null;
        if (header != null && header.startsWith(Constants.AUTHENTICATION_REQUEST_PREFIX)) {
            authenticationToken = header.replace(Constants.AUTHENTICATION_REQUEST_PREFIX, "");
            try {
                username = jwtTokenUtil.getUsernameFromToken(authenticationToken);
            } catch (IllegalArgumentException e) {
                logger.error("an error occured during getting username from token", e);
            } catch (ExpiredJwtException e) {
                logger.warn("the token is expired and not valid anymore", e);
            } catch (SignatureException e) {
                logger.error("Authentication Failed. Username or Password not valid.");
            }
        } else {
            logger.warn("couldn't find bearer string, will ignore the header");
        }
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            String role = getRoleByUsername(username);
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            if (jwtTokenUtil.isTokenExpired(authenticationToken) && jwtTokenUtil.isUsernameValid(authenticationToken, userDetails)) {
                jwtTokenUtil.generateToken(username, role);
            }
            if (!jwtTokenUtil.isTokenExpired(authenticationToken) && jwtTokenUtil.isUsernameValid(authenticationToken, userDetails))
            {


                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, Arrays.asList(new SimpleGrantedAuthority(role)));
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                logger.info("authenticated user " + username + ", setting security context");
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }
        filterChain.doFilter(request, response);
    }

    private String getRoleByUsername(String username) {
        Optional<User> userLogged = userRepository.findByUsername(username);
        String role = null;
        if (userLogged.isPresent()) {
            role = userLogged.get().getRole().getRole().name();
        }
        return role;
    }
}
