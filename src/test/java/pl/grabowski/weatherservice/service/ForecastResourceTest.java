package pl.grabowski.weatherservice.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class ForecastResourceTest {

   /* RestTemplate mockRestTemplate = mock(RestTemplate.class);

    ForecastResource forecastResource = new ForecastResource(mockRestTemplate);

    @Test
    void getForecast() {
        //when
        var result = forecastResource.getForecast(123,456);

        assertThat(result).isEqualTo("https://api.weatherbit.io/v2.0/forecast/daily?&lat=54.695&lon=18.678");
    }*/
}