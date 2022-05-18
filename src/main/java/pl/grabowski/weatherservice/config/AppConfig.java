package pl.grabowski.weatherservice.config;

import org.springframework.amqp.core.Queue;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.time.Clock;
import java.time.Duration;

@Configuration
@EnableCaching
public class AppConfig {

    static final int TIMEOUT = 3000;

    @Bean
    RestTemplate getRestTemplate(){
        return new RestTemplateBuilder()
                .setConnectTimeout(Duration.ofMillis(TIMEOUT))
                .setReadTimeout(Duration.ofMillis(TIMEOUT))
                .build();
    }

    @Bean
    @Profile(value = "prod")
    public Clock clock() {
        return Clock.systemDefaultZone();
    }

    @Bean
    @Profile(value = "prod")
    public Queue myQueue(){
        return new Queue("forecast", false);
    }

    @Configuration
    @PropertySource("classpath:application-prod.yml")
    @Profile(value = "prod")
    static class defaultConfig{
    }
}
