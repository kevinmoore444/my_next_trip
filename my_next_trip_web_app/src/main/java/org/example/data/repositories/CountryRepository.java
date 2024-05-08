package org.example.data.repositories;

import org.example.models.Country;

import java.util.List;

public interface CountryRepository{
    //Find All
    List<Country> findAllCountries();

    //Find Country by ID
    Country findCountryByID(int countryId);

    //Create One
    Country addCountry(Country country);

    //Update One
    boolean updateCountry(Country country);

    //Delete One
    boolean deleteCountryById(int countryId);

    //Find All Countries associated with one User ID (Bucket List)
    List<Country> findallCountriesByUserId(int userId);

    //Add Country Associated with a User ID (Add to Bucket List)
    boolean addToUserCountryList(int appUserId, int countryId);

    //Delete Record From App_User_country (Delete from Bucket List)
    boolean deleteFromUserCountryList(int appUserId, int countryId);
}
