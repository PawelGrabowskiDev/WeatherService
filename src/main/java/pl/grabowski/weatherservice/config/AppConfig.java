package pl.grabowski.weatherservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import pl.grabowski.weatherservice.pojo.AppCity;

@Configuration
public class AppConfig {

    @Bean
    RestTemplate getRestTemplate(){
        return new RestTemplate();
    }
}
