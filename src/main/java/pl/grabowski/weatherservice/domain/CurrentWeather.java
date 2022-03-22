package pl.grabowski.weatherservice.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class CurrentWeather {
    private String cityName;
    private String obsTime;
    private double windSpd;
    private double currentTemp;
    private double pressure;

    @JsonCreator
    public CurrentWeather(
            @JsonProperty("ob_time") String obsTime,
            @JsonProperty("city_name") String cityName,
            @JsonProperty("wind_spd") double windSpd,
            @JsonProperty("temp") double currentTemp,
            @JsonProperty("pres") double pressure){
        this.cityName = cityName;
        this.obsTime = obsTime;
        this.windSpd = windSpd;
        this.currentTemp = currentTemp;
        this.pressure = pressure;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getObsTime() {
        return obsTime;
    }

    public void setObsTime(String obsTime) {
        this.obsTime = obsTime;
    }

    public double getWindSpd() {
        return windSpd;
    }

    public void setWindSpd(double windSpd) {
        this.windSpd = windSpd;
    }

    public double getCurrentTemp() {
        return currentTemp;
    }

    public void setCurrentTemp(double currentTemp) {
        this.currentTemp = currentTemp;
    }

    public double getPressure() {
        return pressure;
    }

    public void setPressure(double pressure) {
        this.pressure = pressure;
    }
}
