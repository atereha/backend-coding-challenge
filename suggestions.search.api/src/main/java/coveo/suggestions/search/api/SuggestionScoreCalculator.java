package coveo.suggestions.search.api;

public interface SuggestionScoreCalculator {

    double calculateScore(Suggestion suggestion);
}
