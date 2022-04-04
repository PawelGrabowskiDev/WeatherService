package pl.grabowski.weatherservice.controller.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@RequiredArgsConstructor
@ToString
public class WeatherDto {
    private final String cityName;
    private final LocalDate date;
    private final double windSpd;
    private final double temp;
}
