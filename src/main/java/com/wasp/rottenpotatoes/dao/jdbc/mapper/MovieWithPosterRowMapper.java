package com.wasp.rottenpotatoes.dao.jdbc.mapper;

import com.wasp.rottenpotatoes.entity.Movie;
import org.springframework.jdbc.core.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;

import static com.wasp.rottenpotatoes.entity.Movie.convertDoubleToBigDecimal;

public class MovieWithPosterRowMapper implements RowMapper<Movie> {
    @Override
    public Movie mapRow(ResultSet rs, int rowNum) throws SQLException {
        return Movie.builder()
            .id(rs.getLong("movie_id"))
            .nameRussian(rs.getString("name_ru"))
            .nameNative(rs.getString("name_en"))
            .releaseYear(rs.getInt("year"))
            .rating(convertDoubleToBigDecimal(rs.getDouble("rating")))
            .price(convertDoubleToBigDecimal(rs.getDouble("price")))
            .posterLink(rs.getString("link"))
            .build();
    }
}
