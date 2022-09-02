package coveo.suggestions.app.config;

import coveo.suggestions.app.controller.SuggestionsController;
import coveo.suggestions.search.api.GeoCoordinateValidator;
import coveo.suggestions.search.api.SuggestionScoreCalculatorFactory;
import coveo.suggestions.search.api.SuggestionsSearchApi;
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

    @Autowired
    private SuggestionScoreCalculatorFactory suggestionScoreCalculatorFactory;

    @Bean
    GeoCoordinateValidator geoCoordinateValidator() {
        return new GeoCoordinateValidator();
    }

}
