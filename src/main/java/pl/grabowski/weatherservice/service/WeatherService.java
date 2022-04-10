package pl.grabowski.weatherservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.grabowski.weatherservice.controller.dto.Weather;
import pl.grabowski.weatherservice.domain.CityForecast;
import pl.grabowski.weatherservice.config.AppCity;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class WeatherService {
    private final ForecastResource forecastResource;
    private final ObjectMapper objectMapper;
    private final AppCity city;

    private CityForecast jsonParseToObject(String json) throws JsonProcessingException {
        return objectMapper.readValue(json, new TypeReference<>() {
        });
    }

    public List<Weather> getForecast(LocalDate date) throws JsonProcessingException {
        List<CityForecast> citiesForecast = new ArrayList<>();
        for (int i = 0; i < city.getCities().size(); i++) {
            var json = forecastResource.getForecast(city.getCities().get(i).get("lat"), city.getCities().get(i).get("lon"));
            citiesForecast.add(jsonParseToObject(json));
        }
        return citiesForecast.stream().map(forecast -> new Weather(
                forecast.getCityName(),
                forecast.getForecastByDate(date).get().getLocalDate(),
                forecast.getForecastByDate(date).get().getWindSpeed(),
                forecast.getForecastByDate(date).get().getTemp()
        )).collect(Collectors.toList());

    }
}
