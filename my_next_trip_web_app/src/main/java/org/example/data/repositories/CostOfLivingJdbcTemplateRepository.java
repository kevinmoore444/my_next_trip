package org.example.data.repositories;


import org.example.data.mappers.CostOfLivingMapper;
import org.example.models.CostOfLiving;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CostOfLivingJdbcTemplateRepository implements CostOfLivingRepository {
    //Dependency
    private final JdbcTemplate jdbcTemplate;

    //Constructor
    public CostOfLivingJdbcTemplateRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    //Find All
    @Override
    public List<CostOfLiving> findCostOfLivingOptions(){
        final String sql = """
                SELECT * 
                FROM cost_of_living
                """;

        return jdbcTemplate.query(sql, new CostOfLivingMapper());
    }



}
