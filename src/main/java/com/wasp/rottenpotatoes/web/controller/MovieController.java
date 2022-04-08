package com.wasp.rottenpotatoes.web.controller;

import com.wasp.rottenpotatoes.entity.Movie;
import com.wasp.rottenpotatoes.entity.SortOrder;
import com.wasp.rottenpotatoes.entity.nbu.Currency;
import com.wasp.rottenpotatoes.request.MovieRequest;
import com.wasp.rottenpotatoes.request.SortBy;
import com.wasp.rottenpotatoes.request.SortingStrategy;
import com.wasp.rottenpotatoes.service.MovieService;
import com.wasp.rottenpotatoes.web.exception.IllegalCurrencyException;
import com.wasp.rottenpotatoes.web.exception.InvalidSortOrderException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.Optional;

@RestController
@RequestMapping(path = "/v1/movie")
@RequiredArgsConstructor
public class MovieController {
    public static final String UAH = "UAH";
    private final MovieService movieService;

    @GetMapping()
    public Iterable<Movie> getAll(@RequestParam Optional<String> rating,
                                  @RequestParam Optional<String> price) {
        MovieRequest movieRequest = new MovieRequest(getSortingStrategy(rating, price));
        validate(movieRequest);
        return movieService.getAll(movieRequest);
    }

    @GetMapping("random")
    public Iterable<Movie> getRandom() {
        return movieService.getRandom();
    }

    @GetMapping("genre")
    public Iterable<Movie> getByGenre(@RequestParam Long genreId,
                                      @RequestParam Optional<String> rating,
                                      @RequestParam Optional<String> price) {
        MovieRequest movieRequest = new MovieRequest(getSortingStrategy(rating, price));
        validate(movieRequest);
        return movieService.getAllByGenre(genreId, movieRequest);
    }

    private SortingStrategy getSortingStrategy(Optional<String> rating, Optional<String> price) {
        if (rating.isPresent()) {
            return new SortingStrategy(SortBy.RATING, SortOrder.valueOf(rating.get().toUpperCase()));
        } else if (price.isPresent()) {
            return new SortingStrategy(SortBy.PRICE, SortOrder.valueOf(price.get().toUpperCase()));
        }
        return null;
    }

    @GetMapping("{id}")
    public Movie getMovieById(@PathVariable Long id, @RequestParam(defaultValue = UAH) String currency) {
        return movieService.getById(id, getCurrencyValue(currency));
    }

    private void validate(MovieRequest movieRequest) {
        SortingStrategy strategy = movieRequest.getSortingStrategy();
        if ((strategy != null)
            && (strategy.getSortBy() == SortBy.RATING)
            && (strategy.getSortOrder() != SortOrder.DESC)) {
            throw new InvalidSortOrderException("Ascending order is not allowed for rating");
        }
    }

    private Currency getCurrencyValue(String currency) {
        try {
            return Currency.valueOf(currency);
        } catch (IllegalArgumentException e) {
            throw new IllegalCurrencyException("Invalid currency parameter: " + currency, e);
        }
    }
}
