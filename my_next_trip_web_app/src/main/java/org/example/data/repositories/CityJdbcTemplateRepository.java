package org.example.data.repositories;

import org.example.data.mappers.CityMapper;
import org.example.models.City;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

@Repository
public class CityJdbcTemplateRepository implements CityRepository{

    //Dependency
    private final JdbcTemplate jdbcTemplate;

    //Constructor
    public CityJdbcTemplateRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    //FindAll By country ID
    @Override
    public List<City> findCitiesByCountryId(int countryId){
        final String sql = """
                SELECT * 
                FROM city
                WHERE country_id = ?
                """;

        return jdbcTemplate.query(sql, new CityMapper(), countryId);
    }


    //Find City By ID
    @Override
    public City findCityByID(int cityId){
        final String sql = """
                SELECT *
                FROM city
                WHERE city_id = ?
                """;
        return jdbcTemplate.query(sql, new CityMapper(), cityId)
                .stream().findFirst().orElse(null);
    }

    //Create
    @Override
    public City addCity(City city) {

        final String sql = """ 
                INSERT into city 
                (city_name, city_description, city_image, country_id) 
                values (?,?,?,?);
                """;

        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        int rowsAffected = jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, city.getName());
            ps.setString(2, city.getDescription());
            ps.setString(3, city.getImage());
            ps.setInt(4, city.getCountryId());
            return ps;
        }, keyHolder);

        if (rowsAffected <= 0) {
            return null;
        }

        city.setId(keyHolder.getKey().intValue());

        return city;
    }

    //Update
    @Override
    public boolean updateCity(City city) {

        final String sql = """
                UPDATE city SET 
                city_name = ?,
                city_image = ?,
                city_description = ?,
                country_id = ?
                WHERE city_id = ?
                """;


        return jdbcTemplate.update(sql,
                city.getName(),
                city.getImage(),
                city.getDescription(),
                city.getCountryId(),
                city.getId()) > 0;
    }

    //Delete
    @Override
    public boolean deleteCityById(int cityId) {
        final String sql = "DELETE FROM city WHERE city_id = ?";
        return jdbcTemplate.update(sql, cityId) > 0;
    }


    //Find All Cities associated with one User ID (Get Bucket List)
    @Override
    public List<City> findAllCitiesByUserId(int userId){
        final String sql = """
                SELECT * FROM city c
                JOIN app_user_city auc on auc.city_id = c.city_id
                where auc.app_user_id = ?
                """;

        return jdbcTemplate.query(sql, new CityMapper(), userId);
    }

    //Is City on user bucket list?
    @Override
    public boolean findOneCityByUserId(int cityId, int userId) {
        final String sql = """
            SELECT COUNT(*) FROM app_user_city auc
            WHERE auc.city_id = ? AND auc.app_user_id = ?
            """;

        try {
            // Execute the query to count matches for the specific city and user
            Integer count = jdbcTemplate.queryForObject(sql, Integer.class, cityId, userId);
            // If result > 0, then a record was found, return true
            return count != null && count > 0;
        } catch (EmptyResultDataAccessException e) {
            // If the query returns nothing (no match), catch and return false
            return false;
        }
    }


    //Add City Associated with a User ID (Add to Bucket List)
    @Override
    public boolean addToUserCityList(int appUserId, int cityId) {
        final String sql = """
                INSERT INTO app_user_city (app_user_id, city_id)
                VALUES (?, ?);
                """;
        return jdbcTemplate.update(sql, appUserId, cityId) > 0;
    }

    //Delete Record From App_User_City (Delete from Bucket List)
    @Override
    public boolean deleteFromUserCityList(int appUserId, int attractionId) {
        final String sql = "DELETE FROM app_user_city where app_user_id = ? AND city_id = ?";
        return jdbcTemplate.update(sql, appUserId, attractionId) > 0;
    }

}
