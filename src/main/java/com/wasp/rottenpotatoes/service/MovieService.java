package com.wasp.rottenpotatoes.service;

import com.wasp.rottenpotatoes.dao.MovieDao;
import com.wasp.rottenpotatoes.entity.Movie;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MovieService {
    private final MovieDao movieDao;

    public Iterable<Movie> getAll() {
        return movieDao.findAll();
    }

    public Iterable<Movie> getRandom() {
        return movieDao.getRandomMovies();
    }

    public Iterable<Movie> getAllByGenre(Long genreId) {
        return movieDao.findAllByGenreId(genreId);
    }
}
