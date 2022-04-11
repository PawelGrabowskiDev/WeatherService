package pl.grabowski.weatherservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.web.client.RestTemplate;
import pl.grabowski.weatherservice.service.ForecastResource;

import java.time.Clock;
import java.time.LocalDate;
import java.time.ZoneId;

@Configuration
public class TestConfig {
    private final static LocalDate DATE = LocalDate.of(2022, 4, 11);

    @Bean
    @Profile(value = "test")
    Clock fixedClock(){
       return  Clock.fixed(DATE.atStartOfDay(ZoneId.systemDefault()).toInstant(),ZoneId.systemDefault());
    }


}
