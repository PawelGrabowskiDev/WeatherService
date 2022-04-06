package pl.grabowski.weatherservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.grabowski.weatherservice.controller.dto.Weather;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class BestWeatherSelector {

    private final WeatherService weatherService;

    @Autowired
    public BestWeatherSelector(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    public Optional<Weather> getBestCity(List<Weather> weather) {
        return weather.stream()
                .filter(forecast -> forecast.getWindSpd() >5 )
                .filter(forecast -> forecast.getWindSpd() < 18 )
                .filter(forecast -> forecast.getTemp() > 5 )
                .filter(forecast -> forecast.getTemp() < 35)
                .max(Comparator.comparing(Weather::calculateBestWeatherValue));
    }
}
