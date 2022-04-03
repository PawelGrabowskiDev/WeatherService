package pl.grabowski.weatherservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import pl.grabowski.weatherservice.config.AppConfig;
import pl.grabowski.weatherservice.domain.CityForecast;
import pl.grabowski.weatherservice.pojo.AppCity;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class WeatherService {
    private final ForecastResource forecastResource;
    private final ObjectMapper objectMapper;
    private final AppConfig config;
    private final AppCity city;

    @Autowired
    public WeatherService(ForecastResource forecastResource, ObjectMapper objectMapper, AppConfig config, AppCity city) {
        this.forecastResource = forecastResource;
        this.objectMapper = objectMapper;
        this.config = config;
        this.city = city;
    }

    private CityForecast jsonParseToObject(String json) throws JsonProcessingException {
        return objectMapper.readValue(json, new TypeReference<>() {
        });
    }

    public List<CityForecast> getForecastCityList() throws JsonProcessingException {
        List<CityForecast> citiesForecast = new ArrayList<>();
        for (int i = 0; i <= 4; i++) {
            var json = forecastResource.getForecast(city.getCities().get(i).get("lat"), city.getCities().get(i).get("lon"));
            citiesForecast.add(jsonParseToObject(json));
        }
        /*citiesForecast.add(jsonParseToObject(forecastResource.getForecast(city.getCities().get(0).get("lat"), city.getCities().get(0).get("lon"))));
        citiesForecast.add(jsonParseToObject(forecastResource.getForecast(city.getCities().get(1).get("lat"), city.getCities().get(1).get("lon"))));
        citiesForecast.add(jsonParseToObject(forecastResource.getForecast(city.getCities().get(2).get("lat"), city.getCities().get(2).get("lon"))));
        citiesForecast.add(jsonParseToObject(forecastResource.getForecast(city.getCities().get(3).get("lat"), city.getCities().get(3).get("lon"))));
        citiesForecast.add(jsonParseToObject(forecastResource.getForecast(city.getCities().get(4).get("lat"), city.getCities().get(4).get("lon"))));
        */
        return citiesForecast;
    }


}
