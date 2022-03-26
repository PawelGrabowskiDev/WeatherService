package pl.grabowski.weatherservice.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.client.RestTemplate;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static pl.grabowski.weatherservice.Config.WeatherbitApiKey.ApiKey;

@RunWith(MockitoJUnitRunner.class)
@ActiveProfiles("test")
class JsonParseServiceImplTest {

    private final String url = "https://api.weatherbit.io/v2.0/current?city=Warsaw&key="+ApiKey;
    private final String jsonResponseFromApi = "src/test/java/pl/grabowski/weatherservice/TestObject/ResponseWeatherbit.json";

    RestTemplate mockRestTemplate = mock(RestTemplate.class);
    ObjectMapper objectMapper = new ObjectMapper();


    private JsonParseServiceImpl parseService = new JsonParseServiceImpl(objectMapper, mockRestTemplate);


    @Test
    void Should_return_json_response_as_java_object() throws IOException {
        //given

        given(mockRestTemplate.getForObject(url, String.class)).willReturn(new String(Files.readAllBytes(Paths.get(jsonResponseFromApi))));
        //when
        var weatherData = parseService.parse(url);
        //then
        assertThat(weatherData.getCurrentWeather().get(0).getCityName()).isEqualTo("Warsaw");
        assertThat(weatherData.getCurrentWeather().get(0).getObsTime()).isEqualTo("2022-03-23 13:35");
        assertThat(weatherData.getCurrentWeather().get(0).getWindSpd()).isEqualTo(2.41125);
    }

}