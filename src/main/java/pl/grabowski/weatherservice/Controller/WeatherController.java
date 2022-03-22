package pl.grabowski.weatherservice.Controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.grabowski.weatherservice.Service.JsonParseService;
import pl.grabowski.weatherservice.domain.CurrentWeather;
import pl.grabowski.weatherservice.domain.WeatherData;

import javax.xml.crypto.Data;
import java.util.List;

@RestController
@RequestMapping(path= "/weather")
@Slf4j
public class WeatherController {

    private final JsonParseService parseService;

    public WeatherController(JsonParseService parseService) {
        this.parseService = parseService;
    }

    @GetMapping
    ResponseEntity<String> getCurrentWeatherFromApi() throws JsonProcessingException {
        WeatherData weatherData =  parseService.parse("https://api.weatherbit.io/v2.0/current?city=Warsaw&key=ae86ab39833e409793ac1abd7d43330d");
        log.info(weatherData.getCurrentWeather().toString());
        return new ResponseEntity<>("", HttpStatus.OK);
    }
}
