package com.wasp.rottenpotatoes.service;

import com.wasp.rottenpotatoes.entity.Movie;
import com.wasp.rottenpotatoes.request.MovieRequest;

public interface MovieService {
    Iterable<Movie> getAll();

    Iterable<Movie> getAll(MovieRequest movieRequest);

    Iterable<Movie> getRandom();

    Iterable<Movie> getAllByGenre(Long genreId, MovieRequest movieRequest);

    Movie getById(Long id);
}
