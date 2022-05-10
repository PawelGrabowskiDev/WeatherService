package pl.grabowski.weatherservice.integration;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;
import pl.grabowski.weatherservice.config.AppCity;
import pl.grabowski.weatherservice.domain.CityForecast;
import pl.grabowski.weatherservice.exceptions.WeatherApiExceptions;

import javax.servlet.UnavailableException;

import static pl.grabowski.weatherservice.config.WeatherbitApiKey.ApiKey;

@Slf4j
@Component
@RequiredArgsConstructor
public class ForecastResource {

    @Value("${forecastResource.weatherApiUrl}")
    private String weatherApiUrl;
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;
    private final AppCity cities;



    private CityForecast jsonParseToObject(String json) throws JsonProcessingException {
        return objectMapper.readValue(json, new TypeReference<>() {
        });
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

    @Cacheable(cacheNames = "Forecast")
    public CityForecast getCity(int index) throws JsonProcessingException {
        var json = getForecast(cities.getCities().get(index).get("lat"), cities.getCities().get(index).get("lon"));
        log.info(json);
        return jsonParseToObject(json);
    }
}

