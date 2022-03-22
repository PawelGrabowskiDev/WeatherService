package pl.grabowski.weatherservice.Controller.Dto;

public class WeatherResponseDto {
    private final String cityName;
    private final String obsTime;
    private final double windSpd;
    private final double currentTemp;
    private final double pressure;

    public WeatherResponseDto(String cityName, String obsTime, double windSpd, double currentTemp, double pressure) {
        this.cityName = cityName;
        this.obsTime = obsTime;
        this.windSpd = windSpd;
        this.currentTemp = currentTemp;
        this.pressure = pressure;
    }

    public String getCityName() {
        return cityName;
    }

    public String getObsTime() {
        return obsTime;
    }

    public double getWindSpd() {
        return windSpd;
    }

    public double getCurrentTemp() {
        return currentTemp;
    }

    public double getPressure() {
        return pressure;
    }
}
