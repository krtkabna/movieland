package com.wasp.rottenpotatoes.service;

import com.wasp.rottenpotatoes.dao.GenreDao;
import com.wasp.rottenpotatoes.entity.Genre;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GenreService {
    private final GenreDao genreDao;

    public Iterable<Genre> getAll() {
        return genreDao.findAll();
    }
}
