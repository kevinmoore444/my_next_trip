package org.example.data.repositories;

import org.example.data.mappers.RegionMapper;
import org.example.models.Region;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RegionJdbcTemplateRepository implements RegionRepository{

    //Dependency
    private final JdbcTemplate jdbcTemplate;

    //Constructor
    public RegionJdbcTemplateRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    //Find All
    @Override
    public List<Region> findAllRegionOptions(){
        final String sql = """
                SELECT * 
                FROM region
                """;

        return jdbcTemplate.query(sql, new RegionMapper());
    }


}
