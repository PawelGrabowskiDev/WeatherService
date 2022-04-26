package pl.grabowski.weatherservice.config;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.client.RestTemplate;

import java.time.Clock;

@Configuration
@EnableCaching
public class AppConfig {

    @Bean
    RestTemplate getRestTemplate(){
        return new RestTemplate();
    }

    @Bean
    @Profile(value = "prod")
    public Clock clock() {
        return Clock.systemDefaultZone();
    }

    @Configuration
    @PropertySource("classpath:application-prod.properties")
    @Profile(value = "prod")
    static class defaultConfig{
    }
}
