package com.wasp.rottenpotatoes.service;

import com.wasp.rottenpotatoes.dao.MovieDao;
import com.wasp.rottenpotatoes.entity.Movie;
import com.wasp.rottenpotatoes.entity.SortOrder;
import com.wasp.rottenpotatoes.exception.InvalidSortOrderException;
import com.wasp.rottenpotatoes.request.MovieRequest;
import com.wasp.rottenpotatoes.request.SortBy;
import com.wasp.rottenpotatoes.request.SortingStrategy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.Optional;
import java.util.function.Supplier;

@Slf4j
@Service
@RequiredArgsConstructor
public class MovieServiceImpl implements MovieService {
    public static final String NO_SUCH_SORT_ORDER = "No such sort order: ";
    public static final String ASCENDING_ORDER_IS_NOT_ALLOWED_FOR_RATING = "Ascending order is not allowed for rating";
    private final MovieDao movieDao;

    @Override
    public Iterable<Movie> getAll() {
        return movieDao.findAll();
    }

    @Override
    public Iterable<Movie> getAll(MovieRequest movieRequest) {
        SortingStrategy sortingStrategy = movieRequest.getSortingStrategy();
        SortBy sortBy = sortingStrategy.getSortBy();

        if (sortBy == SortBy.RATING) {
            return movieDao.findAllSortByRating();
        } else if (sortBy == SortBy.PRICE) {
            return movieDao.findAllSortByPrice(sortingStrategy.getSortOrder());
        } else {
            return movieDao.findAll();
        }
    }

    @Override
    public Iterable<Movie> getRandom(int quantity) {
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
    public Iterable<Movie> getAll(Optional<String> orderByRating, Optional<String> orderByPrice) {
        if (orderByRating.isPresent()) {
            return getSortedByRating(orderByRating.get(), movieDao::findAllSortByRating);
        } else if (orderByPrice.isPresent()) {
            SortOrder order = getSortOrder(orderByPrice.get());
            return movieDao.findAllSortByPrice(order);
        }
        return movieDao.findAll();
    }

    @Override
    public Iterable<Movie> getAllByGenre(Long genreId, Optional<String> orderByRating, Optional<String> orderByPrice) {
        if (orderByRating.isPresent()) {
            return getSortedByRating(orderByRating.get(), () -> movieDao.findAllByGenreIdSortByRating(genreId));
        } else if (orderByPrice.isPresent()) {
            SortOrder order = getSortOrder(orderByPrice.get());
            return movieDao.findAllByGenreIdSortByPrice(genreId, order);
        }
        return movieDao.findAllByGenreId(genreId);
    }

    private SortOrder getSortOrder(String orderByPrice) {
        try {
            return SortOrder.valueOf(orderByPrice.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new InvalidSortOrderException(NO_SUCH_SORT_ORDER + orderByPrice.toUpperCase(), e);
        }
    }

    private Iterable<Movie> getSortedByRating(String order, Supplier<Iterable<Movie>> supplier) {
        if (isDesc(order)) {
            return supplier.get();
        } else {
            throw new InvalidSortOrderException(ASCENDING_ORDER_IS_NOT_ALLOWED_FOR_RATING);
        }
    }
private Iterable<Movie> getSortedByRating(SortOrder order, Supplier<Iterable<Movie>> supplier) {
        if (order == SortOrder.DESC) {
            return supplier.get();
        } else {
            throw new InvalidSortOrderException(ASCENDING_ORDER_IS_NOT_ALLOWED_FOR_RATING);
        }
    }

    private boolean isDesc(String order) {
        try {
            return SortOrder.DESC == (SortOrder.valueOf(order.toUpperCase()));
        } catch (IllegalArgumentException e) {
            throw new InvalidSortOrderException(NO_SUCH_SORT_ORDER + order.toUpperCase(), e);
        }
    }
}
