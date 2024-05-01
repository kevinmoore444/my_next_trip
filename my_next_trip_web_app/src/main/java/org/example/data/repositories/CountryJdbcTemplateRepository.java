package org.example.data.repositories;

import org.example.data.mappers.CountryMapper;
import org.example.models.Country;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

@Repository
public class CountryJdbcTemplateRepository implements CountryRepository{

    //Dependency
    private final JdbcTemplate jdbcTemplate;

    //Constructor
    public CountryJdbcTemplateRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    //Find All
    @Override
    public List<Country> findAllCountries(){
        final String sql = """
                SELECT * 
                FROM country
                INNER JOIN region ON region.region_id = country.region_id
                INNER JOIN cost_of_living ON cost_of_living.cost_of_living_id = country.cost_of_living_id
                """;

        return jdbcTemplate.query(sql, new CountryMapper());
    }

    //Find Country by ID
    @Override
    public Country findCountryByID(int countryId){
        final String sql = """
                SELECT *
                FROM country
                INNER JOIN region ON region.region_id = country.region_id
                INNER JOIN cost_of_living ON cost_of_living.cost_of_living_id = country.cost_of_living_id
                WHERE country_id = ?
                """;
        return jdbcTemplate.query(sql, new CountryMapper(), countryId)
                .stream().findFirst().orElse(null);
    }

    //Create One
    @Override
    public Country addCountry(Country country) {

        final String sql = """ 
                INSERT into country 
                (country_name, country_image, country_description, cost_of_living_id, region_id) 
                values (?,?,?,?,?);
                """;

        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        int rowsAffected = jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, country.getName());
            ps.setString(2, country.getImage());
            ps.setString(3, country.getDescription());
            ps.setInt(4, country.getCostOfLiving().getId());
            ps.setInt(5, country.getRegion().getId());
            return ps;
        }, keyHolder);

        if (rowsAffected <= 0) {
            return null;
        }

        country.setId(keyHolder.getKey().intValue());

        return country;
    }

    //Update One
    @Override
    public boolean updateCountry(Country country) {

        final String sql = """
                UPDATE country SET 
                country_name = ?,
                country_image = ?,
                country_description = ?,
                cost_of_living_id = ?,
                region_id = ?
                WHERE country_id = ?
                """;


        return jdbcTemplate.update(sql,
                country.getName(),
                country.getImage(),
                country.getDescription(),
                country.getCostOfLiving().getId(),
                country.getRegion().getId(),
                country.getId()) > 0;
    }


    //Delete One
    @Override
    public boolean deleteCountryById(int countryId) {
        final String sql = "DELETE from country where country_id = ?";
        return jdbcTemplate.update(sql, countryId) > 0;
    }


}
