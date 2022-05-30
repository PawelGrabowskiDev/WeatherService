package pl.grabowski.weatherservice.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
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
    public Clock clock() {
        return Clock.systemDefaultZone();
    }

    @Bean
    public Queue currentForecastQueue(){
        return new Queue("current_forecast", false);
    }

    @Bean
    public Queue onRequestForecastQueue(){
        return new Queue("forecast_request", false);
    }

    @Bean
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory("localhost");
        connectionFactory.setUsername("guest");
        connectionFactory.setPassword("guest");
        return connectionFactory;
    }

    @Bean
    public Jackson2JsonMessageConverter jsonMessageConverter(){
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.findAndRegisterModules();
        return new Jackson2JsonMessageConverter(objectMapper);
    }

    @Bean
    public RabbitTemplate rabbitTemplate() {
        RabbitTemplate template = new RabbitTemplate(connectionFactory());
        template.setRoutingKey("forecast");
        template.setMessageConverter(jsonMessageConverter());
        return template;
    }


    @Configuration
    @PropertySource("classpath:application-prod.yml")
    static class defaultConfig{
    }
}
