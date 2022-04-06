package com.wasp.rottenpotatoes.service;

import com.wasp.rottenpotatoes.entity.Movie;
import com.wasp.rottenpotatoes.request.MovieRequest;
import java.util.Optional;

public interface MovieService {
    Iterable<Movie> getAll();

    Iterable<Movie> getAllByGenre(Long genreId, MovieRequest movieRequest);

    Iterable<Movie> getAll(Optional<String> orderByRating, Optional<String> orderByPrice);

    Iterable<Movie> getRandom(int quantity);

    Iterable<Movie> getAllByGenre(Long genreId, Optional<String> orderByRating, Optional<String> orderByPrice);

    Iterable<Movie> getAll(MovieRequest movieRequest);
}
