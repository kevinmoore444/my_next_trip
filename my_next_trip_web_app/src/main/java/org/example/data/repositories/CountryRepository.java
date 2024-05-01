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
}
