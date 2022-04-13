package pl.grabowski.weatherservice.service;

import org.springframework.stereotype.Service;
import pl.grabowski.weatherservice.controller.dto.Weather;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class BestWeatherSelector {

    final int minWindSpd = 5;
    final int maxWindSpd = 18;
    final int minTemp = 5;
    final int maxTemp = 35;

    public Optional<Weather> getBestCity(List<Weather> weather) {
        return weather.stream()
                .filter(forecast -> forecast.getWindSpd() > this.minWindSpd)
                .filter(forecast -> forecast.getWindSpd() < this.maxWindSpd)
                .filter(forecast -> forecast.getTemp() > this.minTemp)
                .filter(forecast -> forecast.getTemp() < this.maxTemp)
                .max(Comparator.comparing(Weather::calculateBestWeatherValue));
    }
}
