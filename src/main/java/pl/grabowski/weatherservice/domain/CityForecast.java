package pl.grabowski.weatherservice.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class CityForecast {
    private final List<ForecastWeather> forecastWeatherList;
    private final String cityName;
    private final double lon;
    private final double lat;
    private final String countryCode;

    @JsonCreator
    public CityForecast(
            @JsonProperty("data") List<ForecastWeather> forecastWeatherList,
            @JsonProperty("city_name") String cityName,
            @JsonProperty("lon") double lon,
            @JsonProperty("lat") double lat,
            @JsonProperty("country_code") String countryCode){
        this.forecastWeatherList = forecastWeatherList;
        this.cityName = cityName;
        this.lon = lon;
        this.lat = lat;
        this.countryCode = countryCode;
    }

    public Optional<ForecastWeather> getForecastByDate(LocalDate date){
        return forecastWeatherList
                .stream().
                filter(forecast -> forecast.getLocalDate().equals(date))
                .findAny();
    }

}
