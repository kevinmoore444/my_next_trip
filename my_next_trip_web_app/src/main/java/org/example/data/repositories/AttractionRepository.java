package org.example.data.repositories;

import org.example.models.Attraction;

import java.util.List;

public interface AttractionRepository {
    //FindAll By country ID
    List<Attraction> findAttractionsByCountryId(int countryId);

    //Find Attraction By ID
    Attraction findAttractionByID(int attractionId);

    //Create
    Attraction addAttraction(Attraction attraction);

    //Update
    boolean updateAttraction(Attraction attraction);

    //Delete
    boolean deleteAttractionById(int attractionId);

    //Find All Attractions associated with one User ID (Get Bucket List)
    List<Attraction> findAllAttractionsByUserId(int userId);

    //Add Country Associated with a User ID (Add to Bucket List)
    boolean addToUserAttractionList(int appUserId, int attractionId);

    //Delete Record From App_User_country (Delete from Bucket List)
    boolean deleteFromUserAttractionList(int appUserId, int attractionId);
}
