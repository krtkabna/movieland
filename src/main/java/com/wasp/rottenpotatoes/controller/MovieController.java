package com.wasp.rottenpotatoes.controller;

import com.wasp.rottenpotatoes.entity.Movie;
import com.wasp.rottenpotatoes.service.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/v1/movie")
@RequiredArgsConstructor
public class MovieController {
    private final MovieService movieService;

    @GetMapping()
    public Iterable<Movie> getAll() {
        return movieService.getAll();
    }

    @GetMapping("random")
    public Iterable<Movie> getRandom() {
        return movieService.getRandom();
    }

    @GetMapping("genre")
    public Iterable<Movie> getByGenre(@RequestParam Long genreId) {
        return movieService.getAllByGenre(genreId);
    }
}
