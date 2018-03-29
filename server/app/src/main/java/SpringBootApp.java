import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

@EntityScan(basePackages = {"entity"})
@ComponentScan(basePackages = {"controller", "dao", "service", "validator"})
@SpringBootApplication
public class SpringBootApp extends ResourceServerConfigurerAdapter {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootApp.class, args);
    }
}
