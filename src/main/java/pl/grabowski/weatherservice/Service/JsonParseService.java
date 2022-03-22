package pl.grabowski.weatherservice.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import pl.grabowski.weatherservice.domain.CurrentWeather;
import pl.grabowski.weatherservice.domain.WeatherData;

import java.util.List;

@Slf4j
@Service
public class JsonParseService {

    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper;

    public JsonParseService(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }


    public WeatherData parse(String url) throws JsonProcessingException {
        var weather = restTemplate.getForObject(url, String.class);
        return objectMapper.readValue(weather, new TypeReference<>() {
        });
    }


}
