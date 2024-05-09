package org.example.data.repositories;

import org.example.data.mappers.AttractionMapper;
import org.example.models.Attraction;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

@Repository
public class AttractionJdbcTemplateRepository implements AttractionRepository{

    //Dependency
    private final JdbcTemplate jdbcTemplate;

    //Constructor
    public AttractionJdbcTemplateRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    //FindAll By country ID
    @Override
    public List<Attraction> findAttractionsByCountryId(int countryId){
        final String sql = """
                SELECT * 
                FROM attraction
                WHERE country_id = ?
                """;

        return jdbcTemplate.query(sql, new AttractionMapper(), countryId);
    }


    //Find Attraction By ID
    @Override
    public Attraction findAttractionByID(int attractionId){
        final String sql = """
                SELECT *
                FROM attraction
                WHERE attraction_id = ?
                """;
        return jdbcTemplate.query(sql, new AttractionMapper(), attractionId)
                .stream().findFirst().orElse(null);
    }

    //Create
    @Override
    public Attraction addAttraction(Attraction attraction) {

        final String sql = """ 
                INSERT into attraction 
                (attraction_name, attraction_description, attraction_image, country_id) 
                values (?,?,?,?);
                """;

        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        int rowsAffected = jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, attraction.getName());
            ps.setString(2, attraction.getDescription());
            ps.setString(3, attraction.getImage());
            ps.setInt(4, attraction.getCountryId());
            return ps;
        }, keyHolder);

        if (rowsAffected <= 0) {
            return null;
        }

        attraction.setId(keyHolder.getKey().intValue());

        return attraction;
    }

    //Update
    @Override
    public boolean updateAttraction(Attraction attraction) {

        final String sql = """
                UPDATE attraction SET 
                attraction_name = ?,
                attraction_image = ?,
                attraction_description = ?,
                country_id = ?
                WHERE attraction_id = ?
                """;


        return jdbcTemplate.update(sql,
                attraction.getName(),
                attraction.getImage(),
                attraction.getDescription(),
                attraction.getCountryId(),
                attraction.getId()) > 0;
    }

    //Delete
    @Override
    public boolean deleteAttractionById(int attractionId) {
        final String sql = "DELETE FROM attraction WHERE attraction_id = ?";
        return jdbcTemplate.update(sql, attractionId) > 0;
    }



    //Find All Attractions associated with one User ID (Get Bucket List)
    @Override
    public List<Attraction> findAllAttractionsByUserId(int userId){
        final String sql = """
                SELECT * FROM attraction a
                JOIN app_user_attraction aua on aua.attraction_id = a.attraction_id
                where aua.app_user_id = ?
                """;

        return jdbcTemplate.query(sql, new AttractionMapper(), userId);
    }

    //Is Attraction on User Bucket List?
    @Override
    public boolean findOneAttractionByUserId(int attractionId, int userId) {
        final String sql = """
            SELECT COUNT(*) FROM app_user_attraction aua
            WHERE aua.attraction_id = ? AND aua.app_user_id = ?
            """;
        try {
            // Execute the query to count matches for the specific city and user
            Integer count = jdbcTemplate.queryForObject(sql, Integer.class, attractionId, userId);
            // If result > 0, then a record was found, return true
            return count != null && count > 0;
        } catch (EmptyResultDataAccessException e) {
            // If the query returns nothing (no match), catch and return false
            return false;
        }
    }


    //Add Attraction Associated with a User ID (Add to Bucket List)
    @Override
    public boolean addToUserAttractionList(int appUserId, int attractionId) {
        final String sql = """
                INSERT INTO app_user_attraction (app_user_id, attraction_id)
                VALUES (?, ?);
                """;
        return jdbcTemplate.update(sql, appUserId, attractionId) > 0;
    }

    //Delete Record From App_User_Attraction (Delete from Bucket List)
    @Override
    public boolean deleteFromUserAttractionList(int appUserId, int attractionId) {
        final String sql = "DELETE FROM app_user_attraction where app_user_id = ? AND attraction_id = ?";
        return jdbcTemplate.update(sql, appUserId, attractionId) > 0;
    }


}
