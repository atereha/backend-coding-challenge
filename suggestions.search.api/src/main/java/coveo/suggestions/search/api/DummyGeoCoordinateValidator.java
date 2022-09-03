package coveo.suggestions.search.api;

public class DummyGeoCoordinateValidator implements GeoCoordinateValidator{

    @Override
    public void validate(Double latitude, Double longitude) {
        if (latitude != null) {
            validateLatitude(latitude);
            if (longitude == null) {
                throw new IllegalArgumentException("Only latitude provided. Must be both latitude and longitude");
            }
        }
        if (longitude != null) {
            validateLongitude(longitude);
            if (latitude == null) {
                throw new IllegalArgumentException("Only longitude provided. Must be both longitude and latitude");
            }
        }
    }

    private void validateLatitude(double latitude) {
        //TODO throw IllegalArgumentException if not valid
    }

    private void validateLongitude(double latitude) {
        //TODO throw IllegalArgumentException if not valid
    }
}
