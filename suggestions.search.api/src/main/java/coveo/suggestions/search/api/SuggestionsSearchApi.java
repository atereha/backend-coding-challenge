package coveo.suggestions.search.api;

import java.util.List;

public interface SuggestionsSearchApi {

    /**
     *
     * @param query TODO format
     * @return suggestions list
     */
    List<Suggestion> search(String query);

}
