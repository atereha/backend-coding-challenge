package coveo.suggestions.app.controller;

import coveo.suggestions.search.api.*;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

import java.util.List;
import java.util.stream.Collectors;

@OpenAPIDefinition(info = @Info(title = "Suggestions API", version = "1.0", description = "Suggestions API Description"))
@RestController
public class SuggestionsController {

    @Autowired
    private GeoCoordinateValidator geoCoordinateValidator;

    @Autowired
    private SuggestionsSearchApi suggestionsSearchApi;

    @Autowired
    private SuggestionScoreCalculatorFactory suggestionScoreCalculatorFactory;

    @Operation(summary = "Health check operation")
    @GetMapping("/")
    public String index() {
        return "Hi from SuggestionsController!";
    }

    @Operation(summary = "Search for suggestions")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Suggestions search results",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = SuggestionsDto.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid search request",
                    content = @Content)
    })
    @GetMapping("/suggestions")
    public SuggestionsDto suggestions(@Parameter(description = "Query string to search from by name from start. Case ignored. Must be at least 3 chars size") @RequestParam(value = "q") String query,
                                      @Parameter(description = "Latitude to improve relative scores. Must be set along with the Longitude") @RequestParam(value = "latitude", required = false) Double latitude,
                                      @Parameter(description = "Longitude to improve relative scores. Must be set along with the Latitude") @RequestParam(value = "longitude", required = false) Double longitude) {
        List<Suggestion> suggestions;
        SuggestionScoreCalculator suggestionScoreCalculator;
        if (query.length() < 3) {
            throw new IllegalArgumentException("Query string must contain at least 3 chars");
        }
        if (latitude != null || longitude != null) {
            geoCoordinateValidator.validate(latitude, longitude);
            suggestions = suggestionsSearchApi.search(query);
            suggestionScoreCalculator = suggestionScoreCalculatorFactory.create(query, longitude, latitude);
        } else {
            suggestions = suggestionsSearchApi.search(query);
            suggestionScoreCalculator = suggestionScoreCalculatorFactory.create(query);
        }
        SuggestionsMapper mapper = new SuggestionsMapper(suggestionScoreCalculator);
        List<SuggestionsItemDto> items = suggestions.stream().map(suggestion -> mapper.map(suggestion)).sorted((o1, o2) -> o2.getScore().compareTo(o1.getScore())).collect(Collectors.toList());
        SuggestionsDto dto = new SuggestionsDto();
        dto.setSuggestions(items);
        return dto;
    }

    @ExceptionHandler({IllegalArgumentException.class})
    public ResponseEntity<Object> handleIllegalArgumentException(
            Exception ex, WebRequest request) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("content-type", "text/plain");
        return new ResponseEntity<>(
                "Invalid search request. " + ex.getMessage(), headers, HttpStatus.BAD_REQUEST);
    }
}
