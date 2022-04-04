package pl.grabowski.weatherservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.grabowski.weatherservice.config.AppConfig;
import pl.grabowski.weatherservice.controller.dto.WeatherDto;
import pl.grabowski.weatherservice.domain.CityForecast;
import pl.grabowski.weatherservice.pojo.AppCity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

    public List<WeatherDto> getForecast(LocalDate date) throws JsonProcessingException {
        List<CityForecast> citiesForecast = new ArrayList<>();
        for (int i = 0; i < city.getCities().size(); i++) {
            var json = forecastResource.getForecast(city.getCities().get(i).get("lat"), city.getCities().get(i).get("lon"));
            citiesForecast.add(jsonParseToObject(json));
        }
        var weatherDto = citiesForecast.stream().map(forecast -> new WeatherDto(
                forecast.getCityName(),
                forecast.getForecastByDate(date).get().getLocalDate(),
                forecast.getForecastByDate(date).get().getWindSpeed(),
                forecast.getForecastByDate(date).get().getTemp()
        )).collect(Collectors.toList());

        return weatherDto;
    }
}
