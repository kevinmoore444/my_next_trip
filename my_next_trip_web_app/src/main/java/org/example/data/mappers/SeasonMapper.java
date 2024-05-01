package org.example.data.mappers;

import org.example.models.Season;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SeasonMapper implements RowMapper<Season>{
        @Override
        public Season mapRow(ResultSet rs, int rowNum) throws SQLException {
            Season season = new Season();
            //Compose a season based upon the row returned in the result set. Column labels defined in sql.
            season.setId(rs.getInt("season_id"));
            season.setName(rs.getString("season_name"));
            return season;
        }
}
