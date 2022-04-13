package pl.grabowski.weatherservice.service;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;
import pl.grabowski.weatherservice.config.AppConfig;
import pl.grabowski.weatherservice.config.WeatherbitApiKey;

import javax.servlet.UnavailableException;
import java.net.URI;
import java.nio.charset.Charset;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static pl.grabowski.weatherservice.config.WeatherbitApiKey.ApiKey;

@RunWith(MockitoJUnitRunner.class)
@TestPropertySource
@ActiveProfiles("test")
class ForecastResourceTest {

    RestTemplate mockRestTemplate = mock(RestTemplate.class);
    ForecastResource forecastResource = new ForecastResource(mockRestTemplate);

    @Test
    void getForecast() throws UnavailableException {
        //given
        var validUrl = "https://api.weatherbit.io/v2.0/forecast/daily?&lat=12&lon=43&key="+ ApiKey;
        //when
        given(forecastResource.getForecast(12,43)).willReturn("");
        verify(mockRestTemplate).getForObject(validUrl, String.class);
    }
}