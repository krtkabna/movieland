package com.wasp.rottenpotatoes.dao;

import com.wasp.rottenpotatoes.entity.Movie;
import com.wasp.rottenpotatoes.entity.SortOrder;

public interface MovieDao {
    Iterable<Movie> findAll();

    Iterable<Movie> findAllSortByRating();

    Iterable<Movie> findAllSortByPrice(SortOrder order);

    Iterable<Movie> getRandomMovies(int quantity);

    Iterable<Movie> findAllByGenreId(Long genreId);

    Iterable<Movie> findAllByGenreIdSortByRating(Long genreId);

    Iterable<Movie> findAllByGenreIdSortByPrice(Long genreId, SortOrder order);

    Movie findById(Long id);
}
