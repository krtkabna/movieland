package com.wasp.rottenpotatoes.dao.jdbc;

import com.wasp.rottenpotatoes.dao.MovieDao;
import com.wasp.rottenpotatoes.dao.jdbc.util.MovieResultSetExtractor;
import com.wasp.rottenpotatoes.dao.jdbc.util.MovieWithPosterRowMapper;
import com.wasp.rottenpotatoes.entity.Movie;
import com.wasp.rottenpotatoes.entity.SortOrder;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class JdbcMovieDao implements MovieDao {
    private static final RowMapper<Movie> MOVIE_WITH_POSTER_ROW_MAPPER = new MovieWithPosterRowMapper();
    private static final ResultSetExtractor<Movie> MOVIE_RESULT_SET_EXTRACTOR = new MovieResultSetExtractor();

    private static final String SELECT_WITH_POSTER_TEMPLATE = """
        SELECT movie.movie_id, movie.name_ru, movie.name_en, movie.year, movie.rating, movie.price, poster.link
        FROM movie
        INNER JOIN poster on movie.movie_id = poster.movie_id""";
    private static final String SELECT_ALL = SELECT_WITH_POSTER_TEMPLATE + ";";
    private static final String SELECT_RANDOM = SELECT_WITH_POSTER_TEMPLATE + " ORDER BY random() LIMIT ?";

    private static final String ORDER_BY_MOVIE_RATING_DESC = " ORDER BY movie.rating DESC";
    private static final String ORDER_BY_MOVIE_PRICE_ASC = " ORDER BY movie.price ASC";
    private static final String ORDER_BY_MOVIE_PRICE_DESC = " ORDER BY movie.price DESC";
    private static final String SELECT_ALL_SORT_BY_RATING = SELECT_WITH_POSTER_TEMPLATE + ORDER_BY_MOVIE_RATING_DESC;
    private static final String SELECT_ALL_SORT_BY_PRICE_ASC = SELECT_WITH_POSTER_TEMPLATE + ORDER_BY_MOVIE_PRICE_ASC;
    private static final String SELECT_ALL_SORT_BY_PRICE_DESC = SELECT_WITH_POSTER_TEMPLATE + ORDER_BY_MOVIE_PRICE_DESC;


    private static final String SELECT_ALL_BY_GENRE = SELECT_WITH_POSTER_TEMPLATE + """
                 INNER JOIN movies_genres ON movie.movie_id = movies_genres.movie_id
                 INNER JOIN genre ON movies_genres.genre_id = genre.genre_id
        WHERE genre.genre_id = ?""";
    private static final String SELECT_ALL_BY_GENRE_SORT_BY_RATING = SELECT_ALL_BY_GENRE + ORDER_BY_MOVIE_RATING_DESC;
    private static final String SELECT_ALL_BY_GENRE_SORT_BY_PRICE_ASC = SELECT_ALL_BY_GENRE + ORDER_BY_MOVIE_PRICE_ASC;
    private static final String SELECT_ALL_BY_GENRE_SORT_BY_PRICE_DESC = SELECT_ALL_BY_GENRE + ORDER_BY_MOVIE_PRICE_DESC;

    private static final String SELECT_MOVIE_BY_ID = """
        SELECT movie.movie_id, movie.name_ru, movie.name_en, movie.year, movie.description, movie.rating, movie.price,
               poster.link,
               country.country_id, country.country_name,
               genre.genre_id, genre.genre_name,
               review.review_id, review.content,
               users.user_id, users.user_name
        FROM movie
                INNER JOIN poster ON movie.movie_id = poster.movie_id
                LEFT JOIN movies_genres ON movie.movie_id = movies_genres.movie_id
                LEFT JOIN genre ON movies_genres.genre_id = genre.genre_id
                LEFT JOIN movies_countries mc ON movie.movie_id = mc.movie_id
                LEFT JOIN country ON mc.country_id = country.country_id
                LEFT JOIN review ON movie.movie_id = review.movie_id
                LEFT JOIN users ON review.user_id = users.user_id
        WHERE movie.movie_id = ?;""";


    private final JdbcTemplate jdbcTemplate;

    @Override
    public Iterable<Movie> findAll() {
        return jdbcTemplate.query(SELECT_ALL, MOVIE_WITH_POSTER_ROW_MAPPER);
    }

    @Override
    public Iterable<Movie> findAllSortByRating() {
        return jdbcTemplate.query(SELECT_ALL_SORT_BY_RATING, MOVIE_WITH_POSTER_ROW_MAPPER);
    }

    @Override
    public Iterable<Movie> findAllSortByPrice(SortOrder order) {
        return jdbcTemplate.query(prepareSelectAllSortByPriceStatement(order), MOVIE_WITH_POSTER_ROW_MAPPER);
    }

    @Override
    public Iterable<Movie> getRandomMovies(int quantity) {
        return jdbcTemplate.query(SELECT_RANDOM, ps -> ps.setInt(1, quantity), MOVIE_WITH_POSTER_ROW_MAPPER);
    }

    @Override
    public Iterable<Movie> findAllByGenreId(Long genreId) {
        return jdbcTemplate.query(SELECT_ALL_BY_GENRE, prepareGenreIdStatement(genreId), MOVIE_WITH_POSTER_ROW_MAPPER);
    }

    @Override
    public Iterable<Movie> findAllByGenreIdSortByRating(Long genreId) {
        return jdbcTemplate.query(SELECT_ALL_BY_GENRE_SORT_BY_RATING, prepareGenreIdStatement(genreId), MOVIE_WITH_POSTER_ROW_MAPPER);
    }

    @Override
    public Iterable<Movie> findAllByGenreIdSortByPrice(Long genreId, SortOrder order) {
        return jdbcTemplate.query(prepareSelectByGenreSortByPriceStatement(order), prepareGenreIdStatement(genreId), MOVIE_WITH_POSTER_ROW_MAPPER);
    }

    @Override
    public Movie findById(Long id) {
        return jdbcTemplate.query(SELECT_MOVIE_BY_ID, ps -> ps.setLong(1, id), MOVIE_RESULT_SET_EXTRACTOR);
    }

    private String prepareSelectAllSortByPriceStatement(SortOrder order) {
        return switch (order) {
            case ASC -> SELECT_ALL_SORT_BY_PRICE_ASC;
            case DESC -> SELECT_ALL_SORT_BY_PRICE_DESC;
        };
    }

    private String prepareSelectByGenreSortByPriceStatement(SortOrder order) {
        return switch (order) {
            case ASC -> SELECT_ALL_BY_GENRE_SORT_BY_PRICE_ASC;
            case DESC -> SELECT_ALL_BY_GENRE_SORT_BY_PRICE_DESC;
        };
    }

    private PreparedStatementSetter prepareGenreIdStatement(Long genreId) {
        return ps -> ps.setLong(1, genreId);
    }
}
