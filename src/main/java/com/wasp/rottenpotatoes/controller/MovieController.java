package com.wasp.rottenpotatoes.controller;

import com.wasp.rottenpotatoes.entity.Movie;
import com.wasp.rottenpotatoes.service.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.Optional;

@RestController
@RequestMapping(path = "/v1/movie")
@RequiredArgsConstructor
public class MovieController {
    private final MovieService movieService;

    @GetMapping()
    public Iterable<Movie> getAll(@RequestParam Optional<String> rating,
                                  @RequestParam Optional<String> price) {
        return movieService.getAllSorted(rating, price);
    }

    @GetMapping("random")
    public Iterable<Movie> getRandom(@Value("${movie.random.quantity}") int quantity) {
        return movieService.getRandom(quantity);
    }

    @GetMapping("genre")
    public Iterable<Movie> getByGenre(@RequestParam Long genreId,
                                      @RequestParam Optional<String> rating,
                                      @RequestParam Optional<String> price) {
        return movieService.getAllByGenreSorted(genreId, rating, price);
    }
}
