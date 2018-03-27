import config.CustomUserDetails;
import dao.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

@EntityScan(basePackages = {"entity"})
@ComponentScan(basePackages = {"controller", "dao", "service", "validator"})
@SpringBootApplication
public class SpringBootApp extends ResourceServerConfigurerAdapter {


    @Autowired
    public void authenticationManager(AuthenticationManagerBuilder builder, UserDAO repository) throws Exception {
        //Setup a default user if db is empty
        builder.userDetailsService(userDetailsService(repository));
    }

    /**
     * We return an istance of our CustomUserDetails.
     *
     * @param repository
     * @return
     */
    private UserDetailsService userDetailsService(final UserDAO repository) {
        return username -> new CustomUserDetails(repository.userByUsername(username));
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.headers().frameOptions().disable().and()
                .authorizeRequests()
                .antMatchers("/", "/courses").permitAll();
//                .antMatchers("/private/**").authenticated();
    }

    public static void main(String[] args) {
        SpringApplication.run(SpringBootApp.class, args);
    }
}
