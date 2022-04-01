package pl.grabowski.weatherservice.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.util.List;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class CityForecast {
    private final List<ForecastWeather> forecastWeather;
    private final String cityName;
    private final double lon;
    private final double lat;
    private final String countryCode;

    @JsonCreator
    public CityForecast(
            @JsonProperty("data") List<ForecastWeather> forecastWeather,
            @JsonProperty("city_name") String cityName,
            @JsonProperty("lon") double lon,
            @JsonProperty("lat") double lat,
            @JsonProperty("country_code") String countryCode){
        this.forecastWeather = forecastWeather;
        this.cityName = cityName;
        this.lon = lon;
        this.lat = lat;
        this.countryCode = countryCode;
    }

}
