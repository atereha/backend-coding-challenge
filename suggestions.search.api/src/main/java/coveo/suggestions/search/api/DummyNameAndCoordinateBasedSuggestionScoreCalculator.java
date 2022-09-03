package coveo.suggestions.search.api;

public class DummyNameAndCoordinateBasedSuggestionScoreCalculator extends DummyNameBasedSuggestionScoreCalculator {

    private double latitude;

    private double longitude;

    public DummyNameAndCoordinateBasedSuggestionScoreCalculator(String query, double latitude, double longitude) {
        super(query);
        this.latitude = latitude;
        this.longitude = longitude;
    }

    @Override
    public double calculateScore(Suggestion suggestion) {
        double baseScore = super.calculateScore(suggestion);
        return adjustScore(baseScore, suggestion);
    }

    protected double adjustScore(double baseScore, Suggestion suggestion) {
        //TODO change to real
        return baseScore / 2;
    }
}
