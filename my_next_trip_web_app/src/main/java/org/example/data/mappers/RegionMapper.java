package org.example.data.mappers;

import org.example.models.Region;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class RegionMapper implements RowMapper<Region> {
    @Override
    public Region mapRow(ResultSet rs, int rowNum) throws SQLException {
        Region region = new Region();
        //Compose a season based upon the row returned in the result set. Column labels defined in sql.
        region.setId(rs.getInt("region_id"));
        region.setName(rs.getString("region_name"));
        return region;
    }
}
