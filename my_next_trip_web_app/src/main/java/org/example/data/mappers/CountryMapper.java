package org.example.data.mappers;

import org.example.models.Country;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CountryMapper implements RowMapper<Country> {


    @Override
    public Country mapRow(ResultSet rs, int rowNum) throws SQLException {
        Country country = new Country();
        //Compose a country based upon the row returned in the result set. Column labels defined in sql.
        country.setId(rs.getInt("country_id"));
        country.setName(rs.getString("country_name"));
        country.setImage(rs.getString("country_image"));
        country.setDescription(rs.getString("country_description"));

        CostOfLivingMapper costOfLivingMapper = new CostOfLivingMapper();
        country.setCostOfLiving(costOfLivingMapper.mapRow(rs, rowNum));

        RegionMapper regionMapper = new RegionMapper();
        country.setRegion(regionMapper.mapRow(rs, rowNum));

        return country;
    }


}
