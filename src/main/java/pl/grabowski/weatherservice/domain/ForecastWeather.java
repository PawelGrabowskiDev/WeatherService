package pl.grabowski.weatherservice.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;

import java.time.LocalDate;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ForecastWeather {
    private final double windSpeed;
    private final double maxTemp;
    private final LocalDate localDate;
    private final double temp;
    private final double minTemp;

    @JsonCreator
    public ForecastWeather(
            @JsonProperty("wind_spd") double windSpeed,
            @JsonProperty("max_temp") double maxTemp,
            @JsonProperty("datetime") LocalDate localDate,
            @JsonProperty("temp") double temp,
            @JsonProperty("min_temp") double minTemp) {
        this.windSpeed = windSpeed;
        this.maxTemp = maxTemp;
        this.localDate = localDate;
        this.temp = temp;
        this.minTemp = minTemp;
    }

}
