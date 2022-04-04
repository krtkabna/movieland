package com.wasp.rottenpotatoes.dao;

import com.wasp.rottenpotatoes.entity.Genre;

public interface GenreDao {
    Iterable<Genre> findAll();
}
