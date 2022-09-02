package coveo.suggestions.app.config;

import coveo.suggestions.search.api.*;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({SuggestionsAppConfiguration.class})
public class SuggestionsAppIntegrationTestConfiguration {

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
