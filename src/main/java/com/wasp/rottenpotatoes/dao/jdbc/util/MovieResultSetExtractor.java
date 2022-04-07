package com.wasp.rottenpotatoes.dao.jdbc.util;

import com.wasp.rottenpotatoes.entity.Country;
import com.wasp.rottenpotatoes.entity.Genre;
import com.wasp.rottenpotatoes.entity.Movie;
import com.wasp.rottenpotatoes.entity.Review;
import com.wasp.rottenpotatoes.entity.User;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MovieResultSetExtractor implements ResultSetExtractor<Movie> {
    @Override
    public Movie extractData(ResultSet rs) throws SQLException, DataAccessException {
        Map<Long, Movie> movieMap = new HashMap<>();
        Map<Long, Country> countryMap = new HashMap<>();
        Map<Long, Genre> genreMap = new HashMap<>();
        Map<Long, Review> reviewMap = new HashMap<>();
        Map<Long, User> userMap = new HashMap<>();

        Movie movie = null;
        while (rs.next()) {
            movie = getMovie(rs, movieMap);
            addCountry(rs, countryMap, movie);
            addGenre(rs, genreMap, movie);
            addReview(rs, reviewMap, userMap, movie);
        }
        return movie;
    }

    private Movie getMovie(ResultSet rs, Map<Long, Movie> movieMap) throws SQLException {
        Movie movie;
        Long movieId = rs.getLong("movie_id");
        movie = movieMap.get(movieId);
        if (movie == null) {
            movie = Movie.builder()
                .id(rs.getLong("movie_id"))
                .nameRussian(rs.getString("name_ru"))
                .nameNative(rs.getString("name_en"))
                .releaseYear(rs.getInt("year"))
                .description(rs.getString("description"))
                .rating(rs.getDouble("rating"))
                .price(rs.getDouble("price"))
                .posterLink(rs.getString("link"))
                .build();
        }
        movieMap.put(movieId, movie);
        return movie;
    }

    private void addCountry(ResultSet rs, Map<Long, Country> countryMap, Movie movie) throws SQLException {
        Long countryId = rs.getLong("country_id");
        Country country = countryMap.get(countryId);
        if (country == null) {
            country = Country.builder()
                .id(countryId)
                .name(rs.getString("country_name"))
                .build();
            if (movie.getCountries() == null) {
                movie.setCountries(new ArrayList<>());
            }
            movie.getCountries().add(country);
        }
        countryMap.put(countryId, country);
    }

    private void addGenre(ResultSet rs, Map<Long, Genre> genreMap, Movie movie) throws SQLException {
        Long genreId = rs.getLong("genre_id");
        Genre genre = genreMap.get(genreId);
        if (genre == null) {
            genre = Genre.builder()
                .id(genreId)
                .name(rs.getString("genre_name"))
                .build();
            if (movie.getGenres() == null) {
                movie.setGenres(new ArrayList<>());
            }
            movie.getGenres().add(genre);
        }
        genreMap.put(genreId, genre);
    }

    private void addReview(ResultSet rs, Map<Long, Review> reviewMap, Map<Long, User> userMap, Movie movie) throws SQLException {
        Long reviewId = rs.getLong("review_id");
        Review review = reviewMap.get(reviewId);
        if (review == null) {
            User user = getUser(rs, userMap);

            review = Review.builder()
                .id(reviewId)
                .user(user)
                .content(rs.getString("content"))
                .build();
            if (movie.getReviews() == null) {
                movie.setReviews(new ArrayList<>());
            }
            movie.getReviews().add(review);
        }
        reviewMap.put(reviewId, review);
    }

    private User getUser(ResultSet rs, Map<Long, User> userMap) throws SQLException {
        Long userId = rs.getLong("user_id");
        User user = userMap.get(userId);
        if (user == null) {
            user = User.builder()
                .id(userId)
                .userName(rs.getString("user_name"))
                .build();
        }
        userMap.put(userId, user);
        return user;
    }

}
