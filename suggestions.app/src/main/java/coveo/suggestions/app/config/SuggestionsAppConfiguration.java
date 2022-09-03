package coveo.suggestions.app.config;

import coveo.suggestions.app.controller.SuggestionsController;
import coveo.suggestions.search.api.*;
import coveo.suggestions.search.h2.H2SuggestionsSearchImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({SuggestionsController.class, H2SuggestionsSearchImpl.class})
public class SuggestionsAppConfiguration {

    @Autowired
    private SuggestionsSearchApi suggestionsSearchApi;

    @Bean
    GeoCoordinateValidator geoCoordinateValidator() {
        return new DummyGeoCoordinateValidator();
    }

    @Bean
    SuggestionScoreCalculatorFactory suggestionScoreCalculatorFactory() {
        return new SuggestionScoreCalculatorFactory() {

            @Override
            public SuggestionScoreCalculator create(String query) {
                return new DummyNameBasedSuggestionScoreCalculator(query);
            }

            @Override
            public SuggestionScoreCalculator create(String query, double latitude, double longitude) {
                return new DummyDummyNameAndCoordinateBasedSuggestionScoreCalculator(query, latitude, longitude);
            }
        };
    }

}
