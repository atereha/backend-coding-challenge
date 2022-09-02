package coveo.suggestions.search.api;

public interface SuggestionScoreCalculatorFactory {

    SuggestionScoreCalculator create(String query);

    SuggestionScoreCalculator create(String query, double latitude, double longitude);
}
