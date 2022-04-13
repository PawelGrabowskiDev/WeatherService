package pl.grabowski.weatherservice.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;
import pl.grabowski.weatherservice.exceptions.WeatherApiExceptions;

import javax.servlet.UnavailableException;

import static pl.grabowski.weatherservice.config.WeatherbitApiKey.ApiKey;

@Slf4j
@Service
@PropertySource("classpath:application.properties")
public class ForecastResource {

    @Value("${forecastResource.weatherApiUrl}")
    private String weatherApiUrl;
    private final RestTemplate restTemplate;


    public ForecastResource(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String getForecast(double lat, double lon) {
        UriComponents uriComponents = UriComponentsBuilder
                .fromHttpUrl(this.weatherApiUrl)
                .queryParam("lat", lat)
                .queryParam("lon", lon)
                .queryParam("key",ApiKey)
                .build();
        try {
            return restTemplate.getForObject(uriComponents.toString(), String.class);
        } catch (Exception e) {
            log.info(e.getMessage());
            throw new WeatherApiExceptions(e);
        }
    }
}

