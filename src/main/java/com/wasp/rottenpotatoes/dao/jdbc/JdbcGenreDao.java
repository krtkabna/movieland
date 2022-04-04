package com.wasp.rottenpotatoes.dao.jdbc;

import com.wasp.rottenpotatoes.dao.GenreDao;
import com.wasp.rottenpotatoes.dao.jdbc.util.GenreRowMapper;
import com.wasp.rottenpotatoes.entity.Genre;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class JdbcGenreDao implements GenreDao {
    private static final GenreRowMapper GENRE_ROW_MAPPER = new GenreRowMapper();
    private static final String SELECT_ALL = "SELECT genre_id, genre_name from genre";
    private final JdbcTemplate jdbcTemplate;

    @Override
    public Iterable<Genre> findAll() {
        return jdbcTemplate.query(SELECT_ALL, GENRE_ROW_MAPPER);
    }
}
