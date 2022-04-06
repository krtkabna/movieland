package com.wasp.rottenpotatoes.service;

import com.wasp.rottenpotatoes.dao.MovieDao;
import com.wasp.rottenpotatoes.entity.Movie;
import com.wasp.rottenpotatoes.entity.SortOrder;
import com.wasp.rottenpotatoes.exception.InvalidSortOrderException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class MovieService {
    private final MovieDao movieDao;

    public Iterable<Movie> getAllSorted(Optional<String> orderByRating, Optional<String> orderByPrice) {
        if (orderByRating.isPresent()) {
            if (isDesc(orderByRating.get())) {
                return movieDao.findAllSortByRating();
            } else {
                log.info("Invalid sort order for rating: " + orderByRating.get());
            }
        } else if (orderByPrice.isPresent()) {
            return getAllSortedByPrice(orderByPrice.get());
        }
        return movieDao.findAll();
    }

    public Iterable<Movie> getRandom(int quantity) {
        return movieDao.getRandomMovies(quantity);
    }

    public Iterable<Movie> getAllByGenreSorted(Long genreId, Optional<String> orderByRating, Optional<String> orderByPrice) {
        if (orderByRating.isPresent()) {
            if (isDesc(orderByRating.get())) {
                return movieDao.findAllByGenreIdSortByRating(genreId);
            } else {
                throw new InvalidSortOrderException("Ascending order is not allowed for rating");
            }
        } else if (orderByPrice.isPresent()) {
            return getAllSortedByPrice(orderByPrice.get());
        }
        return movieDao.findAllByGenreId(genreId);
    }

    private Iterable<Movie> getAllSortedByPrice(String direction) {
        Iterable<Movie> result;
        try {
            SortOrder order = SortOrder.valueOf(direction.toUpperCase());
            result = switch (order) {
                case ASC -> movieDao.findAllSortByPriceAsc();
                case DESC -> movieDao.findAllSortByPriceDesc();
            };
        } catch (IllegalArgumentException e) {
            throw new InvalidSortOrderException("No such sort order: " + direction.toUpperCase(), e);
        }
        return result;
    }

    private boolean isDesc(String order) {
        try {
            return SortOrder.DESC.equals(SortOrder.valueOf(order.toUpperCase()));
        } catch (IllegalArgumentException e) {
            throw new InvalidSortOrderException("No such sort order: " + order.toUpperCase(), e);
        }
    }
}
