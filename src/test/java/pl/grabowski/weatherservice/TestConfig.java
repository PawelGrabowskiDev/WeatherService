package pl.grabowski.weatherservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.web.client.RestTemplate;
import pl.grabowski.weatherservice.service.ForecastResource;

@Configuration
public class TestConfig {


/*    @Bean
    RestTemplate getRestTemplate(){
        return new RestTemplate();
    }

    @Bean
    @Profile(value = "test")
    public ForecastResource forecastResource(){
        return new ForecastResource(restTemplate);
    }*/
}
