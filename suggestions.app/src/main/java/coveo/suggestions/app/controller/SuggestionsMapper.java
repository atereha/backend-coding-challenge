package coveo.suggestions.app.controller;

import coveo.suggestions.search.api.Suggestion;
import coveo.suggestions.search.api.SuggestionScoreCalculator;

public class SuggestionsMapper {

    private SuggestionScoreCalculator suggestionScoreCalculator;

    public SuggestionsMapper(SuggestionScoreCalculator suggestionScoreCalculator) {
        this.suggestionScoreCalculator = suggestionScoreCalculator;
    }

    public SuggestionsItemDto map(Suggestion suggestion) {
        SuggestionsItemDto dto = new SuggestionsItemDto();
        dto.setName(suggestion.getName());
        dto.setLatitude(suggestion.getLatitude().toString());
        dto.setLongitude(suggestion.getLongitude().toString());
        dto.setScore(suggestionScoreCalculator.calculateScore(suggestion));
        return dto;
    }
}
