package pl.grabowski.weatherservice.controller.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@RequiredArgsConstructor
@ToString
@EqualsAndHashCode
public class Weather {
    private final String cityName;
    private final LocalDate date;
    private final double windSpd;
    private final double temp;

    public double calculateBestWeatherValue(){
        return this.windSpd * 3 + this.temp;
    }
}
