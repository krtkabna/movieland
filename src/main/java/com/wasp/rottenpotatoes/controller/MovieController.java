package com.wasp.rottenpotatoes.controller;

import com.wasp.rottenpotatoes.entity.Movie;
import com.wasp.rottenpotatoes.service.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/v1")
@RequiredArgsConstructor
public class MovieController {
    private final MovieService movieService;

    @GetMapping(value = "movie")
    public Iterable<Movie> getAll(ModelMap model) {
        Iterable<Movie> movies = movieService.getAll();
        model.addAttribute("movies", movies);
        return movies;
    }

    @GetMapping("random")
    public Iterable<Movie> getRandom(ModelMap model) {
        Iterable<Movie> movies = movieService.getRandom();
        model.addAttribute("movies", movies);
        return movies;
    }
}
