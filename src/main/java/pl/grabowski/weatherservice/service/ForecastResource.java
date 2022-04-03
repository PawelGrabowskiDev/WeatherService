package pl.grabowski.weatherservice.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import static pl.grabowski.weatherservice.config.WeatherbitApiKey.ApiKey;

@Service
@PropertySource("classpath:application.properties")
public class ForecastResource {

    @Value("${forecastResource.weatherApiUrl}")
    private String weatherApiUrl;
    private final RestTemplate restTemplate;


    public ForecastResource(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String getForecast (double lat, double lon) {
        UriComponents uriComponents = UriComponentsBuilder
                .fromHttpUrl(this.weatherApiUrl)
                .queryParam("lat", lat)
                .queryParam("lon", lon)
                .queryParam("key", ApiKey)
                .build();
        return restTemplate.getForObject(uriComponents.toString(), String.class);
    }


}
