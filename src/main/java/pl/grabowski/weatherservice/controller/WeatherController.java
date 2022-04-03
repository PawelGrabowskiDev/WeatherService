package pl.grabowski.weatherservice.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.grabowski.weatherservice.controller.dto.WeatherResponseDto;
import pl.grabowski.weatherservice.service.ForecastResource;
import pl.grabowski.weatherservice.service.WeatherService;

import java.time.LocalDate;

@Slf4j
@RestController
@RequestMapping(path= "/weather")
public class WeatherController {

    private final ForecastResource parseService;
    private final WeatherService weatherService;

    public WeatherController(ForecastResource parseService, WeatherService weatherService) {
        this.parseService = parseService;
        this.weatherService = weatherService;
    }

    /*@GetMapping("/{city}")
    ResponseEntity<WeatherResponseDto> getCurrentWeatherFromApi(@PathVariable(required = true) String city) throws JsonProcessingException {
        WeatherData weatherData =  parseService.parse("https://api.weatherbit.io/v2.0/current?city="+city+"&key="+ApiKey);
        WeatherResponseDto weatherResponse = new WeatherResponseDto(
                weatherData.getCurrentWeather().get(0).getCityName(),
                weatherData.getCurrentWeather().get(0).getObsTime(),
                weatherData.getCurrentWeather().get(0).getWindSpd(),
                weatherData.getCurrentWeather().get(0).getCurrentTemp(),
                weatherData.getCurrentWeather().get(0).getPressure()
        );
        return new ResponseEntity<>(weatherResponse, HttpStatus.OK);
    }*/

    @GetMapping
    ResponseEntity<WeatherResponseDto> getBestWeatherFromApiByDay(
            @RequestParam("date")
            @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate date) throws JsonProcessingException {
            if(date.isAfter(LocalDate.now().minusDays(1)) && date.isBefore(LocalDate.now().plusDays(17))){
                log.info("Date is Ok");
                var list = weatherService.getForecastCityList();
            }
        return new ResponseEntity<>(null, HttpStatus.OK);
    }
}
