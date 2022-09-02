package coveo.suggestions.search.api;

public class NameBasedSuggestionScoreCalculator implements SuggestionScoreCalculator {

    private String query;

    public NameBasedSuggestionScoreCalculator(String query) {
        this.query = query;
    }

    @Override
    public double calculateScore(Suggestion suggestion) {
        //TODO calculate score
        return 0;
    }
}
