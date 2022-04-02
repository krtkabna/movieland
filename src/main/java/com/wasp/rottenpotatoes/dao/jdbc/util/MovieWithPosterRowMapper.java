package com.wasp.rottenpotatoes.dao.jdbc.util;

import com.wasp.rottenpotatoes.entity.Movie;
import org.springframework.jdbc.core.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MovieWithPosterRowMapper implements RowMapper<Movie> {
    @Override
    public Movie mapRow(ResultSet rs, int rowNum) throws SQLException {
        return Movie.builder()
            .id(rs.getLong("movie_id"))
            .nameRussian(rs.getString("name_ru"))
            .nameNative(rs.getString("name_en"))
            .releaseYear(rs.getInt("year"))
            .rating(rs.getDouble("rating"))
            .price(rs.getDouble("price"))
            .posterLink(rs.getString("link"))
            .build();
    }
}
