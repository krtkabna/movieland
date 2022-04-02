package com.wasp.rottenpotatoes.dao;

import com.wasp.rottenpotatoes.entity.Movie;

public interface MovieDao {
    Iterable<Movie> findAll();

    Iterable<Movie> getRandomMovies();
}
