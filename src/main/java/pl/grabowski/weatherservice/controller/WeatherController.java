package pl.grabowski.weatherservice.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.grabowski.weatherservice.service.WeatherService;

import java.time.LocalDate;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping(path= "/weather")
public class WeatherController {

    private final WeatherService weatherService;
    private final RabbitTemplate rabbitTemplate;

    @GetMapping
    ResponseEntity<?> getBestWeatherFromApiByDay(@RequestParam("date") @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate date) throws JsonProcessingException {
        if(!weatherService.isValidDate(date)){
            return ResponseEntity.badRequest().body("Date is wrong!");
        }
        var bestWeatherResponse = weatherService.getBestWeather(date);
        bestWeatherResponse.ifPresent(rabbitTemplate::convertAndSend);
        return bestWeatherResponse
                .map(weather -> new ResponseEntity<>(weather, HttpStatus.OK))
                .orElseGet(() -> ResponseEntity.noContent().build());
    }
}
