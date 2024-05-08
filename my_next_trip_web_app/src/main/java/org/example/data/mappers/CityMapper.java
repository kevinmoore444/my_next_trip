package org.example.data.mappers;

import org.example.models.City;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CityMapper implements RowMapper<City> {

    @Override
    public City mapRow(ResultSet rs, int rowNum) throws SQLException {
        City city = new City();
        //Compose a city based upon the row returned in the result set. Column labels defined in sql.
        city.setId(rs.getInt("city_id"));
        city.setName(rs.getString("city_name"));
        city.setDescription(rs.getString("city_description"));
        city.setImage(rs.getString("city_image"));
        city.setCountryId(rs.getInt("country_id"));
        return city;
    }
}
