package coveo.suggestions.search.api;

public interface GeoCoordinateValidator {

    /**
     * Throws IllegalArgumentException if validation failed
     * @param latitude
     * @param longitude
     */
    void validate(Double latitude, Double longitude);
}
