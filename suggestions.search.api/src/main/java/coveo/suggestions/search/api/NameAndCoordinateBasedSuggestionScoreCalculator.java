package coveo.suggestions.search.api;

public class NameAndCoordinateBasedSuggestionScoreCalculator extends NameBasedSuggestionScoreCalculator{

    private double latitude;

    private double longitude;

    public NameAndCoordinateBasedSuggestionScoreCalculator(String query, double latitude, double longitude) {
        super(query);
        this.latitude = latitude;
        this.longitude = longitude;
    }

    @Override
    public double calculateScore(Suggestion suggestion) {
        double baseScore = super.calculateScore(suggestion);
        return adjustScore(baseScore, suggestion);
    }

    public double adjustScore(double baseScore, Suggestion suggestion) {
        //TODO adjust score
        return baseScore;
    }
}
