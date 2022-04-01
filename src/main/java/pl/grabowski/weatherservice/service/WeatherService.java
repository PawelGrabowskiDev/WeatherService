package pl.grabowski.weatherservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.grabowski.weatherservice.domain.CityForecast;
import java.util.ArrayList;
import java.util.List;

import static pl.grabowski.weatherservice.config.WeatherbitApiKey.ApiKey;

@Service
@Slf4j
public class WeatherService {
    /*private final String JastarniaUrl = "https://api.weatherbit.io/v2.0/forecast/daily?&lat=54.695&lon=18.678&key="+ApiKey;
    private final String BridgetownUrl = "https://api.weatherbit.io/v2.0/forecast/daily?&lat=13.105&lon=-59.613&key="+ApiKey;
    private final String FortalezaUrl = "https://api.weatherbit.io/v2.0/forecast/daily?&lat=-3.732&lon=-38.527&key="+ApiKey;
    private final String PissouriUrl = "https://api.weatherbit.io/v2.0/forecast/daily?&lat=34.664&lon=32.706&key="+ApiKey;
    private final String LeMorneUrl = "https://api.weatherbit.io/v2.0/forecast/daily?&lat=-20.445&lon=57.328&key="+ApiKey;*/

    private final ForecastResource forecastResource;
    private final ObjectMapper objectMapper;

    @Autowired
    public WeatherService(ForecastResource forecastResource, ObjectMapper objectMapper) {
        this.forecastResource = forecastResource;
        this.objectMapper = objectMapper;
    }

    private CityForecast jsonParse(String json) throws JsonProcessingException {
        return objectMapper.readValue(json, new TypeReference<>() {
        });

    }

    public List<CityForecast> getForecastListFromApi() throws JsonProcessingException {

        log.info(jsonParse(forecastResource.getForecast(54.695,18.678)).toString());
        List<CityForecast> weatherDataList = new ArrayList<>();
        return weatherDataList;
    }
}
