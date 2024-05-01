package org.example.data.mappers;

import org.example.models.Attraction;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AttractionMapper implements RowMapper<Attraction>{
        @Override
        public Attraction mapRow(ResultSet rs, int rowNum) throws SQLException {
            Attraction attraction = new Attraction();
            //Compose an attraction based upon the row returned in the result set. Column labels defined in sql.
            attraction.setId(rs.getInt("attraction_id"));
            attraction.setName(rs.getString("attraction_name"));
            attraction.setDescription(rs.getString("attraction_description"));
            attraction.setImage(rs.getString("attraction_image"));
            return attraction;
        }
}
