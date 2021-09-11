package atflourenco.golden.raspberry.awards;

import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ApplicationTests {

    private final HttpHeaders headers = new HttpHeaders();
    private final TestRestTemplate template = new TestRestTemplate();

    @LocalServerPort
    private int port;

    @Test
    public void testMinMaxIntervalBetweenTwoAwards() throws Exception {
        HttpEntity<String> entity = new HttpEntity<>(null, headers);
        ResponseEntity<String> response = template.exchange(createURLWithPort("/movies/min-max-interval-awards"), HttpMethod.GET, entity, String.class);
        String expected = "{\"min\":[{\"producer\":\"Adam McKay\",\"interval\":0,\"previousWin\":2018,\"followingWin\":2018}]," +
                "\"max\":[{\"producer\":\"Matthew Vaughn\",\"interval\":49,\"previousWin\":2015,\"followingWin\":2064}]}";
        JSONAssert.assertEquals(expected, response.getBody(), false);
    }

    @Test
    public void testStatusCodeSuccess() {
        ResponseEntity<String> response = template.getForEntity(createURLWithPort("/movies/min-max-interval-awards"),
                String.class);
        assertThat(response.getStatusCodeValue()).isEqualTo(200);
    }


    private String createURLWithPort(String uri) {
        return "http://localhost:" + port + uri;
    }
}
