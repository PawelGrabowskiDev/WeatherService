package pl.grabowski.weatherservice;

import com.fasterxml.jackson.core.JsonProcessingException;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import com.github.tomakehurst.wiremock.junit.WireMockRule;
import com.jayway.jsonpath.JsonPath;
import org.junit.Rule;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.*;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static pl.grabowski.weatherservice.config.WeatherbitApiKey.ApiKey;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class WeatherServiceApplicationIT {

    @LocalServerPort
    private int port;

    @Rule
    WireMockServer wireMock = new WireMockServer ( WireMockConfiguration.wireMockConfig().port(9090));

    @Autowired
    TestRestTemplate restTemplate;

    @BeforeEach
    void startWireMock(){
        wireMock.start();
        WireMock.configureFor("localhost", wireMock.port());
    }

    @AfterEach
    void stopWireMock(){
        wireMock.stop();
    }
    void firstCity(String bodyJson) {
        stubFor(get(urlPathEqualTo("/test/weather/api"))
                .withQueryParam("lat", equalTo("54.695"))
                .withQueryParam("lon", equalTo("18.678"))
                .willReturn(aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withStatus(200)
                        .withBodyFile(bodyJson)));
    }

    void secondCity(String bodyJson){
        stubFor(get(urlPathEqualTo("/test/weather/api"))
                .withQueryParam("lat", equalTo("13.105"))
                .withQueryParam("lon", equalTo("-59.613"))
                .willReturn(aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withStatus(200)
                        .withBodyFile(bodyJson)));
    }


    @Test
    void should_build_valid_url() throws JsonProcessingException {
        //given

        //when

    }

    @Test
    void should_return_the_city_that_meets_good_the_conditions(){
        //given
        firstCity("BadConditions.json");
        secondCity("GoodConditions.json");
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
        firstCity("BadConditions.json");
        secondCity("BadConditions.json");
        UriComponents uriComponents = UriComponentsBuilder
                .fromHttpUrl("http://localhost:"+port+"/weather")
                .queryParam("date", "12-04-2022")
                .build();

        var result = restTemplate.getForEntity(uriComponents.toString(), String.class);
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
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
