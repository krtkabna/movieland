package com.wasp.rottenpotatoes.dao.jdbc.util;

import com.wasp.rottenpotatoes.entity.Genre;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GenreResultSetExtractor implements ResultSetExtractor<Genre> {

    @Override
    public Genre extractData(ResultSet rs) throws SQLException, DataAccessException {
        return Genre.builder()
            .id(rs.getLong("genre_id"))
            .name(rs.getString("genre_id"))
            .build();
    }
}
