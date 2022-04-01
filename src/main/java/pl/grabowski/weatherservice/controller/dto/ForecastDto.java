package pl.grabowski.weatherservice.controller.dto;

import java.time.LocalDate;

public class ForecastDto {
    private final String cityName;
    private final double lon;
    private final double lat;
    private final String countryCode;
    private final double windSpeed;
    private final double maxTemp;
    private final LocalDate localDate;
    private final double temp;
    private final double minTemp;

    public ForecastDto(String cityName, double lon, double lat, String countryCode, double windSpeed, double maxTemp, LocalDate localDate, double temp, double minTemp) {
        this.cityName = cityName;
        this.lon = lon;
        this.lat = lat;
        this.countryCode = countryCode;
        this.windSpeed = windSpeed;
        this.maxTemp = maxTemp;
        this.localDate = localDate;
        this.temp = temp;
        this.minTemp = minTemp;
    }

    public String getCityName() {
        return cityName;
    }

    public double getLon() {
        return lon;
    }

    public double getLat() {
        return lat;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public double getWindSpeed() {
        return windSpeed;
    }

    public double getMaxTemp() {
        return maxTemp;
    }

    public LocalDate getLocalDate() {
        return localDate;
    }

    public double getTemp() {
        return temp;
    }

    public double getMinTemp() {
        return minTemp;
    }
}
