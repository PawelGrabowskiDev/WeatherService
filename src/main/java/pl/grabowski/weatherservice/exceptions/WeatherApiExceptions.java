package pl.grabowski.weatherservice.exceptions;

public class WeatherApiExceptions extends RuntimeException {
    public WeatherApiExceptions(Exception exception) {
        super("Weather Api error: "+exception.getMessage());
    }
}
