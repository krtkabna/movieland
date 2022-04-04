package com.wasp.rottenpotatoes.dao.jdbc;

import com.wasp.rottenpotatoes.dao.GenreDao;
import com.wasp.rottenpotatoes.dao.jdbc.util.GenreResultSetExtractor;
import com.wasp.rottenpotatoes.dao.jdbc.util.GenreRowMapper;
import com.wasp.rottenpotatoes.entity.Genre;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import java.util.HashMap;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class JdbcGenreDao implements GenreDao {
    private static final GenreRowMapper GENRE_ROW_MAPPER = new GenreRowMapper();
    private static final GenreResultSetExtractor GENRE_RESULT_SET_EXTRACTOR = new GenreResultSetExtractor();
    private static final String SELECT_ALL = "SELECT genre_id, genre_name from genre";
    private static final String SELECT_BY_ID = "SELECT genre_id, genre_name from genre WHERE genre_id=?";
    private final JdbcTemplate jdbcTemplate;

    private Map<Long, Genre> cache = new HashMap<>();

    @Override
    public Iterable<Genre> findAll() {
        return jdbcTemplate.query(SELECT_ALL, GENRE_ROW_MAPPER);
    }

    public Genre findById(Long id) {
        if (cache.containsKey(id)) {
            return cache.get(id);
        }
        return getGenreFromDB(id);
    }

    private Genre getGenreFromDB(Long id) {
        return jdbcTemplate.query(SELECT_BY_ID, ps -> ps.setLong(1, id), GENRE_RESULT_SET_EXTRACTOR);
    }

}
