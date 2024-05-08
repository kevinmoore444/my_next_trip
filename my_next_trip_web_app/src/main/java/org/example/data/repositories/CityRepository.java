package org.example.data.repositories;

import org.example.models.City;

import java.util.List;

public interface CityRepository {


    //FindAll By country ID
    List<City> findCitiesByCountryId(int countryId);

    //Find City By ID
    City findCityByID(int cityId);

    //Create
    City addCity(City city);

    //Update
    boolean updateCity(City city);

    //Delete
    boolean deleteCityById(int cityId);

    //Find All Cities associated with one User ID (Get Bucket List)
    List<City> findAllCitiesByUserId(int userId);

    //Add City Associated with a User ID (Add to Bucket List)
    boolean addToUserCityList(int appUserId, int cityId);

    //Delete Record From App_User_City (Delete from Bucket List)
    boolean deleteFromUserCityList(int appUserId, int attractionId);

    //Is City on user bucket list?
    boolean findOneCityByUserId(int cityId, int userId);
}
