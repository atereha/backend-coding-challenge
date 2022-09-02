package coveo.suggestions.search.api;

public class DummyNameBasedSuggestionScoreCalculator implements SuggestionScoreCalculator {

    private String query;

    public DummyNameBasedSuggestionScoreCalculator(String query) {
        this.query = query;
    }

    @Override
    public double calculateScore(Suggestion suggestion) {
        //TODO change to real
        return (double) Math.round(10 * query.length() / suggestion.getName().length()) / 10;
    }
}
