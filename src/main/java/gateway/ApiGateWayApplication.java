package gateway;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

/**
 * author Hao
 * date 2025/8/7 14:45
 */
@SpringBootApplication
public class ApiGateWayApplication {
    public static void main(String[] args) {
//        org.springframework.boot.SpringApplication.run(ApiGateWayApplication.class, args);
        new SpringApplicationBuilder(ApiGateWayApplication.class).web(WebApplicationType.NONE).run(args);

    }
}
