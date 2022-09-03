package coveo.suggestions.search.api;

public interface SuggestionScoreCalculator {

    /**
     * Calculates suggestion score
     * @param suggestion
     * @return
     */
    double calculateScore(Suggestion suggestion);
}
