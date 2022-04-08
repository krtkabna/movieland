package com.wasp.rottenpotatoes.dao.jdbc.mapper;

import com.wasp.rottenpotatoes.entity.Genre;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GenreRowMapper implements RowMapper<Genre> {

    @Override
    public Genre mapRow(ResultSet rs, int rowNum) throws SQLException, DataAccessException {
        return Genre.builder()
            .id(rs.getLong("genre_id"))
            .name(rs.getString("genre_name"))
            .build();
    }
}
