package coveo.suggestions.app;

import coveo.suggestions.app.config.SuggestionsAppIntegrationTestConfiguration;
import coveo.suggestions.app.controller.SuggestionsDto;
import coveo.suggestions.app.controller.SuggestionsItemDto;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@Import(SuggestionsAppIntegrationTestConfiguration.class)
@TestPropertySource(locations = {"classpath:application.test.properties"})
public class SuggestionsAppIntegrationTest {

    private static String BASE_URL = "http://localhost:8080";

    private RestTemplate restTemplate = new RestTemplate();

    private List<SuggestionsItemDto> expectedSuggestionsForLondo;

    private List<SuggestionsItemDto> expectedSuggestionsForLondoWithCoord;

    @Before
    public void setUp() {
        expectedSuggestionsForLondo = new ArrayList<>() {
            {
                add(new SuggestionsItemDto("London, ON, Canada", "42.98339", "-81.23304", (double) Math.round(10 * "londo".length() / ("London, ON, Canada".length())) / 10));
                add(new SuggestionsItemDto("London, OH, USA", "39.88645", "-83.44825", (double) Math.round(10 * "londo".length() / ("London, OH, USA".length())) / 10));
                add(new SuggestionsItemDto("London, KY, USA", "37.12898", "-84.08326", (double) Math.round(10 * "londo".length() / ("London, KY, USA".length())) / 10));
                add(new SuggestionsItemDto("Londontowne, MD, USA", "38.93345", "-76.54941", (double) Math.round(10 * "londo".length() / ("Londontowne, MD, USA".length())) / 10));
            }
        };
        expectedSuggestionsForLondoWithCoord = new ArrayList<>() {
            {
                add(new SuggestionsItemDto("London, ON, Canada", "42.98339", "-81.23304", (double) Math.round(10 * "londo".length() / ("London, ON, Canada".length())) / 20));
                add(new SuggestionsItemDto("London, OH, USA", "39.88645", "-83.44825", (double) Math.round(10 * "londo".length() / ("London, OH, USA".length())) / 20));
                add(new SuggestionsItemDto("London, KY, USA", "37.12898", "-84.08326", (double) Math.round(10 * "londo".length() / ("London, KY, USA".length())) / 20));
                add(new SuggestionsItemDto("Londontowne, MD, USA", "38.93345", "-76.54941", (double) Math.round(10 * "londo".length() / ("Londontowne, MD, USA".length())) / 20));
            }
        };
    }

    @Test
    public void testSuccessGetRoot() {
        ResponseEntity<String> response = restTemplate.getForEntity(BASE_URL + "/", String.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testSuccessGetSuggestionsWithoutCoords() {
        ResponseEntity<SuggestionsDto> response = restTemplate.getForEntity(BASE_URL + "/suggestions?q=Londo", SuggestionsDto.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        SuggestionsDto suggestions = response.getBody();
        assertEquals(4, suggestions.getSuggestions().size());
        assertTrue(expectedSuggestionsForLondo.contains(suggestions.getSuggestions().get(0)));
        assertTrue(expectedSuggestionsForLondo.contains(suggestions.getSuggestions().get(1)));
        assertTrue(expectedSuggestionsForLondo.contains(suggestions.getSuggestions().get(2)));
        assertTrue(expectedSuggestionsForLondo.contains(suggestions.getSuggestions().get(3)));
        validateOrder(suggestions.getSuggestions());
    }

    @Test
    public void testSuccessGetSuggestionsWithoutCoordsLowcase() {
        ResponseEntity<SuggestionsDto> response = restTemplate.getForEntity(BASE_URL + "/suggestions?q=londo", SuggestionsDto.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        SuggestionsDto suggestions = response.getBody();
        assertEquals(4, suggestions.getSuggestions().size());
        assertTrue(expectedSuggestionsForLondo.contains(suggestions.getSuggestions().get(0)));
        assertTrue(expectedSuggestionsForLondo.contains(suggestions.getSuggestions().get(1)));
        assertTrue(expectedSuggestionsForLondo.contains(suggestions.getSuggestions().get(2)));
        assertTrue(expectedSuggestionsForLondo.contains(suggestions.getSuggestions().get(3)));
        validateOrder(suggestions.getSuggestions());
    }

    @Test
    public void testSuccessGetSuggestionsWithCoords() {
        ResponseEntity<SuggestionsDto> response = restTemplate.getForEntity(BASE_URL + "/suggestions?q=Londo&latitude=0.0&longitude=0.0", SuggestionsDto.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        SuggestionsDto suggestions = response.getBody();
        assertEquals(4, suggestions.getSuggestions().size());
        assertTrue(expectedSuggestionsForLondoWithCoord.contains(suggestions.getSuggestions().get(0)));
        assertTrue(expectedSuggestionsForLondoWithCoord.contains(suggestions.getSuggestions().get(1)));
        assertTrue(expectedSuggestionsForLondoWithCoord.contains(suggestions.getSuggestions().get(2)));
        assertTrue(expectedSuggestionsForLondoWithCoord.contains(suggestions.getSuggestions().get(3)));
        validateOrder(suggestions.getSuggestions());
    }

    @Test
    public void testSuccessGetSuggestionsWithCoordsLowcase() {
        ResponseEntity<SuggestionsDto> response = restTemplate.getForEntity(BASE_URL + "/suggestions?q=londo&latitude=0.0&longitude=0.0", SuggestionsDto.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        SuggestionsDto suggestions = response.getBody();
        assertEquals(4, suggestions.getSuggestions().size());
        assertTrue(expectedSuggestionsForLondoWithCoord.contains(suggestions.getSuggestions().get(0)));
        assertTrue(expectedSuggestionsForLondoWithCoord.contains(suggestions.getSuggestions().get(1)));
        assertTrue(expectedSuggestionsForLondoWithCoord.contains(suggestions.getSuggestions().get(2)));
        assertTrue(expectedSuggestionsForLondoWithCoord.contains(suggestions.getSuggestions().get(3)));
        validateOrder(suggestions.getSuggestions());
    }

    @Test
    public void testNoMatchGetSuggestionsWithoutCoords() {
        ResponseEntity<SuggestionsDto> response = restTemplate.getForEntity(BASE_URL + "/suggestions?q=SomeRandomCityInTheMiddleOfNowhere", SuggestionsDto.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        SuggestionsDto suggestions = response.getBody();
        assertEquals(0, suggestions.getSuggestions().size());

    }

    @Test
    public void testNoMatchGetSuggestionsWithCoords() {
        ResponseEntity<SuggestionsDto> response = restTemplate.getForEntity(BASE_URL + "/suggestions?q=SomeRandomCityInTheMiddleOfNowhere&latitude=0.0&longitude=0.0", SuggestionsDto.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        SuggestionsDto suggestions = response.getBody();
        assertEquals(0, suggestions.getSuggestions().size());
    }

    @Test(expected = HttpClientErrorException.BadRequest.class)
    public void testBsdRequestGetToShortQuery() throws Exception {
        ResponseEntity<SuggestionsDto> response = restTemplate.getForEntity(BASE_URL + "/suggestions?q=lo&latitude=0.0&longitude=0.0", SuggestionsDto.class);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test(expected = HttpClientErrorException.BadRequest.class)
    public void testBadRequestGetForLondoWithCoordMissedLatitude() throws Exception {
        ResponseEntity<SuggestionsDto> response = restTemplate.getForEntity(BASE_URL + "/suggestions?q=londo&latitude=0.0", SuggestionsDto.class);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test(expected = HttpClientErrorException.BadRequest.class)
    public void testBadRequestGetForLondoWithCoordMissedLongitude() throws Exception {
        ResponseEntity<SuggestionsDto> response = restTemplate.getForEntity(BASE_URL + "/suggestions?q=londo&longitude=0.0", SuggestionsDto.class);
    }

    private static void validateOrder(List<SuggestionsItemDto> suggestions) {
        double prev = 1.0;
        for (SuggestionsItemDto suggestion : suggestions) {
            assertFalse(prev < suggestion.getScore());
            prev = suggestion.getScore();
        }
    }
}