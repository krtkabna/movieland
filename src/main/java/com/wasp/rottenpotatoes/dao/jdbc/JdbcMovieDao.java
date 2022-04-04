package com.wasp.rottenpotatoes.dao.jdbc;

import com.wasp.rottenpotatoes.dao.MovieDao;
import com.wasp.rottenpotatoes.dao.jdbc.util.MovieWithPosterRowMapper;
import com.wasp.rottenpotatoes.entity.Movie;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class JdbcMovieDao implements MovieDao {
    private static final MovieWithPosterRowMapper MOVIE_WITH_POSTER_ROW_MAPPER = new MovieWithPosterRowMapper();
    private static final String SELECT_WITH_POSTER_TEMPLATE =
        "SELECT movie.movie_id, movie.name_ru, movie.name_en, movie.year, movie.rating, movie.price, poster.link\n" +
            "FROM movie\n" +
            "INNER JOIN poster on movie.movie_id = poster.movie_id\n";
    private static final String SELECT_ALL = SELECT_WITH_POSTER_TEMPLATE + ";";
    private static final String SELECT_RANDOM = SELECT_WITH_POSTER_TEMPLATE +
        "ORDER BY random()\n" +
        "LIMIT 3;";
    private final JdbcTemplate jdbcTemplate;

    @Override
    public Iterable<Movie> findAll() {
        return jdbcTemplate.query(SELECT_ALL, MOVIE_WITH_POSTER_ROW_MAPPER);
    }

    @Override
    public Iterable<Movie> getRandomMovies() {
        return jdbcTemplate.query(SELECT_RANDOM, MOVIE_WITH_POSTER_ROW_MAPPER);
    }
}
