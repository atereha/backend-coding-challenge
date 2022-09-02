package coveo.suggestions.search.h2;

import coveo.suggestions.search.api.Suggestion;
import coveo.suggestions.search.api.SuggestionsSearchApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Component
public class H2SuggestionsSearchImpl implements SuggestionsSearchApi {

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    @Override
    public List<Suggestion> search(String query) {

        return jdbcTemplate.query("SELECT geonameid, name, latitude, longitude FROM Suggestions WHERE name ILIKE :query", new MapSqlParameterSource("query", query + "%"), new RowMapper<Suggestion>() {
            @Override
            public Suggestion mapRow(ResultSet rs, int rowNum) throws SQLException {
                Suggestion suggestion = new Suggestion();
                suggestion.setGeonameid(rs.getInt("geonameid"));
                suggestion.setName(rs.getString("name"));
                suggestion.setLatitude(rs.getDouble("latitude"));
                suggestion.setLongitude(rs.getDouble("longitude"));
                return suggestion;
            }
        });

    }
}
