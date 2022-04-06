package com.wasp.rottenpotatoes.dao;

import com.wasp.rottenpotatoes.entity.Movie;

public interface MovieDao {
    Iterable<Movie> findAll();

    Iterable<Movie> findAllSortByRating();

    Iterable<Movie> findAllSortByPriceAsc();

    Iterable<Movie> findAllSortByPriceDesc();

    Iterable<Movie> getRandomMovies(int quantity);

    Iterable<Movie> findAllByGenreId(Long genreId);

    Iterable<Movie> findAllByGenreIdSortByRating(Long genreId);

    Iterable<Movie> findAllByGenreIdSortByPriceAsc(Long genreId);

    Iterable<Movie> findAllByGenreIdSortByPriceDesc(Long genreId);
}
