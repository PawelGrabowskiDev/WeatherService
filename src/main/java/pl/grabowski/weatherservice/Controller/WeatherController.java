package pl.grabowski.weatherservice.Controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.grabowski.weatherservice.Controller.Dto.WeatherResponseDto;
import pl.grabowski.weatherservice.Service.JsonParseService;
import pl.grabowski.weatherservice.Service.JsonParseServiceImpl;
import pl.grabowski.weatherservice.domain.WeatherData;

import static pl.grabowski.weatherservice.Config.WeatherbitApiKey.*;


@RestController
@RequestMapping(path= "/weather")
public class WeatherController {

    private final JsonParseServiceImpl parseService;

    public WeatherController(JsonParseServiceImpl parseService) {
        this.parseService = parseService;
    }

    @GetMapping("/{city}")
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
    }
}
