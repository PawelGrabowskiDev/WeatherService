package pl.grabowski.weatherservice.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties
public class CurrentWeather {
    private String cityName;
    private double windSpd;
    private double currentTemp;

    @JsonCreator
    public CurrentWeather(@JsonProperty("city_name") String city_name,
                          @JsonProperty("wind_spd") double wind_spd,
                          @JsonProperty("temp")  double temp) {
        this.cityName = city_name;
        this.windSpd = wind_spd;
        this.currentTemp = temp;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
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

    @Override
    public String toString() {
        return "CurrentWeather{" +
                "cityName='" + cityName + '\'' +
                ", windSpd=" + windSpd +
                ", currentTemp=" + currentTemp +
                '}';
    }
}
