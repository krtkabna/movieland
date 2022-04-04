package com.wasp.rottenpotatoes.controller;

import com.wasp.rottenpotatoes.entity.Genre;
import com.wasp.rottenpotatoes.service.GenreService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/v1")
@RequiredArgsConstructor
public class GenreController {
    private final GenreService genreService;

    @GetMapping("genre")
    public Iterable<Genre> getAll() {
        return genreService.getAll();
    }
}
