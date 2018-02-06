package utilityService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;


@EnableWebSecurity
@Configuration
//@EnableGlobalMethodSecurity(prePostEnabled = true)
public class LoginService extends WebSecurityConfigurerAdapter {


    @Autowired
    private  UserLoginService userLoginService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.authorizeRequests().antMatchers("**/", "/user/addUser", "/auth").permitAll().antMatchers("/courses/**","**/courses","**/courses/*").permitAll(); //.hasAnyRole("ADMIN").anyRequest().authenticated();
        //htt.formLogin().permitAll();
    }

    @Autowired
    protected void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {

       auth.userDetailsService(this.userLoginService);
    }
}
