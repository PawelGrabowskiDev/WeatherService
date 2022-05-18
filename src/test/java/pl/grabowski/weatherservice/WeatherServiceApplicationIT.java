package pl.grabowski.weatherservice;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;
import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@TestPropertySource(properties={"net.sf.ehcache.disabled=true"})
class WeatherServiceApplicationIT {

    @LocalServerPort
    private int port;

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
    void should_pass_the_error_returned_by_weather_api(){
        stubFor(get(urlPathEqualTo("/test/weather/api"))
                        .withQueryParam("lat", equalTo("1000"))
                        .withQueryParam("lon", equalTo("1000"))
                .willReturn(aResponse().withBody("")));

        UriComponents url = UriComponentsBuilder
                .fromHttpUrl("http://localhost:"+port+"/weather")
                .queryParam("date", "12-04-2022")
                .build();
       var result = restTemplate.getForEntity(url.toString(), String.class);
       assertThat(result.getBody()).contains("Weather Api error: ");

    }

    @Test
    void should_return_the_city_that_meets_good_the_conditions(){
        //given
        firstCity("BadConditions.json");
        secondCity("GoodConditions.json");
        UriComponents url = UriComponentsBuilder
                .fromHttpUrl("http://localhost:"+port+"/weather")
                .queryParam("date", "12-04-2022")
                .build();

        var result = restTemplate.getForEntity(url.toString(), String.class);
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
        UriComponents url = UriComponentsBuilder
                .fromHttpUrl("http://localhost:"+port+"/weather")
                .queryParam("date", "12-04-2022")
                .build();

        var result = restTemplate.getForEntity(url.toString(), String.class);
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }

    @Test
    void should_return_error_if_date_is_not_valid(){
        UriComponents url = UriComponentsBuilder
                .fromHttpUrl("http://localhost:"+port+"/weather")
                .queryParam("date", "27-05-2022")
                .build();

        var result = restTemplate.getForEntity(url.toString(),String.class);
        assertThat(result.getStatusCodeValue()).isEqualTo(400);
        assertThat(result.getBody()).isEqualTo("Date is wrong!");
    }

    @Test
    void Should_return_response_as_json() {
        //given
        firstCity("BadConditions.json");
        secondCity("GoodConditions.json");
        UriComponents url = UriComponentsBuilder
                .fromHttpUrl("http://localhost:"+port+"/weather")
                .queryParam("date", "12-04-2022")
                .build();
        //when
        var result = restTemplate.getForEntity(url.toString(), String.class);

        // then
        assertThat(result.getStatusCode().is2xxSuccessful()).isTrue();
        assertThat(result.getHeaders().getContentType()).isEqualTo(MediaType.APPLICATION_JSON);
    }

}
