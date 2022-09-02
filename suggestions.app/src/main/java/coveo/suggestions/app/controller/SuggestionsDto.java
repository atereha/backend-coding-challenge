package coveo.suggestions.app.controller;

import java.util.List;

public class SuggestionsDto {

    private List<SuggestionsItemDto> suggestions;

    public List<SuggestionsItemDto> getSuggestions() {
        return suggestions;
    }

    public void setSuggestions(List<SuggestionsItemDto> suggestions) {
        this.suggestions = suggestions;
    }
}
