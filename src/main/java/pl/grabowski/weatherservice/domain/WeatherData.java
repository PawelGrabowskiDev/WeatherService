package pl.grabowski.weatherservice.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class WeatherData {
    public List<CurrentWeather> currentWeather;

    @JsonCreator
    public WeatherData(@JsonProperty("data") List<CurrentWeather> currentWeather) {
        this.currentWeather = currentWeather;
    }

    public List<CurrentWeather> getCurrentWeather() {
        return currentWeather;
    }

    public void setCurrentWeather(List<CurrentWeather> currentWeather) {
        this.currentWeather = currentWeather;
    }
}
