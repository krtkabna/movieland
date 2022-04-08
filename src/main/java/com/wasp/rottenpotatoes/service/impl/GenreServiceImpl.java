package com.wasp.rottenpotatoes.service.impl;

import com.wasp.rottenpotatoes.dao.GenreDao;
import com.wasp.rottenpotatoes.entity.Genre;
import com.wasp.rottenpotatoes.service.GenreService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GenreServiceImpl implements GenreService {
    private final GenreDao genreDao;

    @Override
    public Iterable<Genre> getAll() {
        return genreDao.findAll();
    }
}
