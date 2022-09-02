package coveo.suggestions.app.controller;

import coveo.suggestions.search.api.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(SuggestionsController.class)
public class SuggestionsControllerTest {

    @MockBean
    private SuggestionsSearchApi suggestionsSearchApi;

    @MockBean
    private SuggestionScoreCalculatorFactory suggestionScoreCalculatorFactory;

    @Autowired
    private MockMvc mvc;


    @Before
    public void setUp() {
        List<SuggestionsItemDto> suggestionsItemDtos = new ArrayList<>() {
            {
                add(new SuggestionsItemDto("Londontowne, MD, USA", "38.93345", "-76.54941", 0.3));
                add(new SuggestionsItemDto("London, OH, USA", "39.88645", "-83.44825", 0.5));
                add(new SuggestionsItemDto("London, KY, USA", "37.12898", "-84.08326", 0.5));
                add(new SuggestionsItemDto("London, ON, Canada", "42.98339", "-81.23304", 0.9));

            }
        };
        Mockito.when(suggestionsSearchApi.search("Londo"))
                .thenReturn(suggestionsItemDtos.stream().map(dto -> toSuggestions(dto)).collect(Collectors.toList()));

        SuggestionScoreCalculator suggestionScoreCalculatorMock = new SuggestionScoreCalculator() {

            @Override
            public double calculateScore(Suggestion suggestion) {
                return suggestionsItemDtos.stream().filter(suggestionsItemDto -> suggestion.getName().equals(suggestionsItemDto.getName())).findFirst().get().getScore();
            }
        };

        Mockito.when(suggestionScoreCalculatorFactory.create(any()))
                .thenReturn(suggestionScoreCalculatorMock);
        Mockito.when(suggestionScoreCalculatorFactory.create(any(), anyDouble(), anyDouble()))
                .thenReturn(suggestionScoreCalculatorMock);

    }

    private Suggestion toSuggestions(SuggestionsItemDto dto) {
        Suggestion suggestion = new Suggestion();
        suggestion.setName(dto.getName());
        suggestion.setLatitude(Double.valueOf(dto.getLatitude()));
        suggestion.setLongitude(Double.valueOf(dto.getLongitude()));
        return suggestion;
    }


    @Test
    public void testSuccessGetRoot() throws Exception {
        mvc.perform(get("/")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }


    @Test
    public void testSuccessGetSuggestionsWithoutCoords() throws Exception {


        mvc.perform(get("/suggestions")
                        .queryParam("q", "Londo")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$['suggestions']", hasSize(4)))
                .andExpect(jsonPath("$['suggestions'][0].name", is("London, ON, Canada")))
                .andExpect(jsonPath("$['suggestions'][0].latitude", is("42.98339")))
                .andExpect(jsonPath("$['suggestions'][0].longitude", is("-81.23304")))
                .andExpect(jsonPath("$['suggestions'][0].score", is(0.9)))
                .andExpect(jsonPath("$['suggestions'][1].name", is("London, OH, USA")))
                .andExpect(jsonPath("$['suggestions'][1].latitude", is("39.88645")))
                .andExpect(jsonPath("$['suggestions'][1].longitude", is("-83.44825")))
                .andExpect(jsonPath("$['suggestions'][1].score", is(0.5)))
                .andExpect(jsonPath("$['suggestions'][2].name", is("London, KY, USA")))
                .andExpect(jsonPath("$['suggestions'][2].latitude", is("37.12898")))
                .andExpect(jsonPath("$['suggestions'][2].longitude", is("-84.08326")))
                .andExpect(jsonPath("$['suggestions'][2].score", is(0.5)))
                .andExpect(jsonPath("$['suggestions'][3].name", is("Londontowne, MD, USA")))
                .andExpect(jsonPath("$['suggestions'][3].latitude", is("38.93345")))
                .andExpect(jsonPath("$['suggestions'][3].longitude", is("-76.54941")))
                .andExpect(jsonPath("$['suggestions'][3].score", is(0.3)));
    }

    @Test
    public void testSuccessGetSuggestionsWithCoords() throws Exception {
        mvc.perform(get("/suggestions")
                        .queryParam("q", "Londo")
                        .queryParam("latitude", "43.70011")
                        .queryParam("longitude", "-79.4163")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$['suggestions']", hasSize(4)))
                .andExpect(jsonPath("$['suggestions'][0].name", is("London, ON, Canada")))
                .andExpect(jsonPath("$['suggestions'][0].latitude", is("42.98339")))
                .andExpect(jsonPath("$['suggestions'][0].longitude", is("-81.23304")))
                .andExpect(jsonPath("$['suggestions'][0].score", is(0.9)))
                .andExpect(jsonPath("$['suggestions'][1].name", is("London, OH, USA")))
                .andExpect(jsonPath("$['suggestions'][1].latitude", is("39.88645")))
                .andExpect(jsonPath("$['suggestions'][1].longitude", is("-83.44825")))
                .andExpect(jsonPath("$['suggestions'][1].score", is(0.5)))
                .andExpect(jsonPath("$['suggestions'][2].name", is("London, KY, USA")))
                .andExpect(jsonPath("$['suggestions'][2].latitude", is("37.12898")))
                .andExpect(jsonPath("$['suggestions'][2].longitude", is("-84.08326")))
                .andExpect(jsonPath("$['suggestions'][2].score", is(0.5)))
                .andExpect(jsonPath("$['suggestions'][3].name", is("Londontowne, MD, USA")))
                .andExpect(jsonPath("$['suggestions'][3].latitude", is("38.93345")))
                .andExpect(jsonPath("$['suggestions'][3].longitude", is("-76.54941")))
                .andExpect(jsonPath("$['suggestions'][3].score", is(0.3)));
    }

    @Test
    public void testNoMatchGetSuggestionsWithoutCoords() throws Exception {
        mvc.perform(get("/suggestions")
                        .queryParam("q", "SomeRandomCityInTheMiddleOfNowhere")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$['suggestions']", hasSize(0)));
    }

    @Test
    public void testBsdRequestGetToShortQuery() throws Exception {
        mvc.perform(get("/suggestions")
                        .queryParam("q", "lo")
                        .queryParam("latitude", "43.70011")
                        .queryParam("longitude", "-79.4163")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testBadRequestGetForLondoWithCoordMissedLatitude() throws Exception {
        mvc.perform(get("/suggestions")
                        .queryParam("latitude", "43.70011")
                        //.queryParam("longitude","-79.4163")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testBadRequestGetForLondoWithCoordMissedLongitude() throws Exception {
        mvc.perform(get("/suggestions")
                        //.queryParam("latitude", "43.70011")
                        .queryParam("longitude", "-79.4163")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }
}
