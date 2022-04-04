package pl.grabowski.weatherservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.grabowski.weatherservice.controller.dto.WeatherDto;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class BestWeatherSelector {

    private final WeatherService weatherService;

    @Autowired
    public BestWeatherSelector(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    public Optional<List<WeatherDto>> getBestCity(LocalDate date) throws JsonProcessingException {

        return Optional.empty();
    }
}
