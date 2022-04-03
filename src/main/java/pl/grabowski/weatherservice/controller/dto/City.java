package pl.grabowski.weatherservice.controller.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class City {
    private final String cityName;
    private final double lon;
    private final double lat;
    private String countryCode;
    private double windSpeed;
    private double maxTemp;
    private LocalDate localDate;
    private double temp;
    private double minTemp;

    public City(String cityName, double lon, double lat) {
        this.cityName = cityName;
        this.lon = lon;
        this.lat = lat;
    }
}
