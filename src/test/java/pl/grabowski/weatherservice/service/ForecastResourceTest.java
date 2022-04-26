package pl.grabowski.weatherservice.service;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.client.RestTemplate;
import pl.grabowski.weatherservice.integration.ForecastResource;

import static pl.grabowski.weatherservice.config.WeatherbitApiKey.ApiKey;

@ExtendWith(MockitoExtension.class)
@PropertySource("classpath:application-test.properties")
@ActiveProfiles("test")
class ForecastResourceTest {

    @Mock
    RestTemplate mockRestTemplate;
    ForecastResource forecastResource;

    @BeforeEach
    public void setup(){
        forecastResource = new ForecastResource("http://localhost:9090/test/weather/api", mockRestTemplate);
    }

    @Test
    void should_build_valid_url_to_external_api(){
        //given
        var validUrl = "http://localhost:9090/test/weather/api?lat=12.0&lon=43.0&key="+ ApiKey;
        //when
        forecastResource.getForecast(12.0,43.0);
        //Then
        Mockito.verify(mockRestTemplate).getForObject(validUrl, String.class);
    }

    @Test
    void should_pass_error_from_rest_template(){
        //given

        //when
        forecastResource.getForecast(12.0,43.0);
        //Then

    }
}