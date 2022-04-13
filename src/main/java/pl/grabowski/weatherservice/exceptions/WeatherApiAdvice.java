package pl.grabowski.weatherservice.exceptions;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class WeatherApiAdvice {
    @ResponseBody
    @ExceptionHandler(WeatherApiExceptions.class)
    public String weatherApiHandler(WeatherApiExceptions ex){
        return ex.getMessage();
    }
}
