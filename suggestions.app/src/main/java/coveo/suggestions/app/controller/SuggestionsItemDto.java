package coveo.suggestions.app.controller;

import java.util.Objects;

public class SuggestionsItemDto {

    private String name;

    private String latitude;

    private String longitude;

    private Double score;

    public SuggestionsItemDto() {

    }

    public SuggestionsItemDto(String name, String latitude, String longitude, Double score) {
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.score = score;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SuggestionsItemDto that = (SuggestionsItemDto) o;
        return Objects.equals(name, that.name) && Objects.equals(latitude, that.latitude) && Objects.equals(longitude, that.longitude) && Objects.equals(score, that.score);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, latitude, longitude, score);
    }
}
