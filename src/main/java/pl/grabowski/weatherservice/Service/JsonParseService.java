package pl.grabowski.weatherservice.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import pl.grabowski.weatherservice.domain.WeatherData;

public interface JsonParseService {
    WeatherData parse(String url) throws JsonProcessingException;
}
