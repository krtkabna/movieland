package com.wasp.rottenpotatoes.service;

import com.wasp.rottenpotatoes.entity.Genre;

public interface GenreService {
    Iterable<Genre> getAll();
}
