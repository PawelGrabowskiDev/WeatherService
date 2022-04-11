package pl.grabowski.weatherservice;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.stubbing.StubMapping;
import com.jayway.jsonpath.JsonPath;
import org.junit.Rule;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.*;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willReturn;
import static org.mockito.Mockito.mockStatic;
import static pl.grabowski.weatherservice.config.WeatherbitApiKey.ApiKey;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class WeatherServiceApplicationIT {

    private final static LocalDate DATE = LocalDate.of(2022, 5, 1);

    @LocalServerPort
    private int port;

    @Rule
    WireMockServer wireMock = new WireMockServer(9090);

    @Autowired
    TestRestTemplate restTemplate;

    /*ForecastResource forecastResource = new ForecastResource(restTemplate);
    ObjectMapper objectMapper = new ObjectMapper();
    AppCity appCity = new AppCity();
    private final WeatherService weatherService = new WeatherService(forecastResource, objectMapper, appCity);*/

    @BeforeEach
    void startWireMock(){
        wireMock.start();
    }

    void goodConditionsDataResponse() {
        //Good conditions Bridgetown
        WireMock.configureFor("localhost", wireMock.port());
        stubFor(get("https://api.weatherbit.io/v2.0/forecast/daily?&lat=13.105&lon=-59.613&key=" + ApiKey)
                .willReturn(aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withBodyFile("src/test/java/pl/grabowski/weatherservice/TestObject/GoodConditions.json")));
    }
    void badConditionsDataResponse(){
        //Bad conditions Jastarnia
        stubFor(get("https://api.weatherbit.io/v2.0/forecast/daily?&lat=54.695&lon=18.678&key="+ApiKey)
                .willReturn(aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withBodyFile("src/test/java/pl/grabowski/weatherservice/TestObject/BadConditions.json")));
    }

    @AfterEach
    void stopWireMock(){
        wireMock.stop();
    }


    @Test
    void should_build_valid_url() throws JsonProcessingException {
        //given

        //when

    }

    @Test
    void should_return_the_city_that_meets_good_the_conditions(){
        //given
        goodConditionsDataResponse();
        badConditionsDataResponse();
        UriComponents uriComponents = UriComponentsBuilder
                .fromHttpUrl("http://localhost:"+port+"/weather")
                .queryParam("date", "12-04-2022")
                .build();

        var result = restTemplate.getForEntity(uriComponents.toString(), String.class);
        var json = JsonPath.parse(result.getBody());
        assertThat(result.getStatusCodeValue()).isEqualTo(200);
        assertThat(json.read("$.cityName", String.class)).isEqualTo("Bridgetown");
        assertThat(json.read("$.windSpd", Double.class)).isEqualTo(7.3);
        assertThat(json.read("$.temp", Double.class)).isEqualTo(26);
    }

    @Test
    void should_return_no_content_when_no_city_has_good_conditions(){
        badConditionsDataResponse();
        UriComponents uriComponents = UriComponentsBuilder
                .fromHttpUrl("http://localhost:"+port+"/weather")
                .queryParam("date", "12-04-2022")
                .build();

        var result = restTemplate.getForEntity(uriComponents.toString(), String.class);
        var json = JsonPath.parse(result.getBody());
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }

    @Test
    void should_allow_only_date_from_now_to_16_days_forward(){
    }

    @Test
    void should_return_error_if_date_is_invalid(){
       //Clock clock = Clock.fixed(Instant.parse("2014-12-22T10:15:30.00Z"), ZoneId.of("UTC"));
        UriComponents uriComponents = UriComponentsBuilder
                .fromHttpUrl("http://localhost:"+port+"/weather")
                .queryParam("date", "27-05-2022")
                .build();

        var result = restTemplate.getForEntity(uriComponents.toString(),String.class);
        assertThat(result.getStatusCodeValue()).isEqualTo(400);
        assertThat(result.getBody()).isEqualTo("Date is wrong!");
    }

}
