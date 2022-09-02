package coveo.suggestions.search.api;

public class GeoCoordinateValidator {

    public void validate(Double latitude, Double longitude) {
        if (latitude != null) {
            validateLatitude(latitude);
            if (longitude == null) {
                throw new IllegalArgumentException("Only latitude provided");
            }
        }
        if (longitude != null) {
            validateLongitude(longitude);
            if (latitude == null) {
                throw new IllegalArgumentException("Only longitude provided");
            }
        }
    }

    private void validateLatitude(Double latitude) {
        //TODO throw IllegalArgumentException if not valid
    }

    private void validateLongitude(Double latitude) {
        //TODO throw IllegalArgumentException if not valid
    }
}
