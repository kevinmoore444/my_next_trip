package org.example.data.mappers;

import org.example.models.CostOfLiving;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CostOfLivingMapper implements RowMapper<CostOfLiving> {

    @Override
    public CostOfLiving mapRow(ResultSet rs, int rowNum) throws SQLException {
        CostOfLiving costOfLiving = new CostOfLiving();
        //Compose a season based upon the row returned in the result set. Column labels defined in sql.
        costOfLiving.setId(rs.getInt("cost_of_living_id"));
        costOfLiving.setName(rs.getString("cost_of_living_name"));
        return costOfLiving;
    }
}
