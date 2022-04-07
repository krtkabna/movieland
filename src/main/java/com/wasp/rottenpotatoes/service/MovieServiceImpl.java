package com.wasp.rottenpotatoes.service;

import com.wasp.rottenpotatoes.dao.MovieDao;
import com.wasp.rottenpotatoes.entity.Movie;
import com.wasp.rottenpotatoes.entity.nbu.Currency;
import com.wasp.rottenpotatoes.exception.MovieNotFoundException;
import com.wasp.rottenpotatoes.request.MovieRequest;
import com.wasp.rottenpotatoes.request.SortBy;
import com.wasp.rottenpotatoes.request.SortingStrategy;
import com.wasp.rottenpotatoes.service.nbu.RateService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.time.LocalDate;

@Slf4j
@Service
@RequiredArgsConstructor
public class MovieServiceImpl implements MovieService {
    private final MovieDao movieDao;
    private final RateService rateService;

    private @Value("${movie.random.quantity}")
    int quantity;

    @Override
    public Iterable<Movie> getAll() {
        return movieDao.findAll();
    }

    @Override
    public Iterable<Movie> getAll(MovieRequest movieRequest) {
        SortingStrategy sortingStrategy = movieRequest.getSortingStrategy();
        if (sortingStrategy == null) {
            return movieDao.findAll();
        }

        SortBy sortBy = sortingStrategy.getSortBy();
        return switch (sortBy) {
            case RATING -> movieDao.findAllSortByRating();
            case PRICE -> movieDao.findAllSortByPrice(sortingStrategy.getSortOrder());
        };
    }

    @Override
    public Iterable<Movie> getRandom() {
        return movieDao.getRandomMovies(quantity);
    }

    @Override
    public Iterable<Movie> getAllByGenre(Long genreId, MovieRequest movieRequest) {
        SortingStrategy sortingStrategy = movieRequest.getSortingStrategy();
        SortBy sortBy = sortingStrategy.getSortBy();

        if (sortBy == SortBy.RATING) {
            return movieDao.findAllByGenreIdSortByRating(genreId);
        } else if (sortBy == SortBy.PRICE) {
            return movieDao.findAllByGenreIdSortByPrice(genreId, sortingStrategy.getSortOrder());
        } else {
            return movieDao.findAllByGenreId(genreId);
        }
    }

    @Override
    public Movie getById(Long id) {
        Movie movie = movieDao.findById(id);
        if (movie == null) {
            throw new MovieNotFoundException("No movie found by id: " + id);
        }
        return movie;
    }

    @Override
    public Movie getById(Long id, Currency currency) {
        Movie movie = getById(id);
        if (Currency.UAH != currency) {
            double rate = rateService.getRate(currency, LocalDate.now());
            movie.setPrice(movie.getPrice() * rate);
        }
        return movie;
    }
}
