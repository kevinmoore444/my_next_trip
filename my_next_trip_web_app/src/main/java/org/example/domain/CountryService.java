package org.example.domain;


import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.example.data.repositories.CountryRepository;
import org.example.models.Country;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class CountryService {

    //Dependencies
    private final CountryRepository countryRepository;

    //Constructor
    public CountryService(CountryRepository countryRepository) {
        this.countryRepository = countryRepository;
    }

    //Find All
    public List<Country> findAllCountries() {
        return countryRepository.findAllCountries();
    }

    //Find By ID
    public Country findById(int countryId) {
        return countryRepository.findCountryByID(countryId);
    }

    //Add Country
    public Result<Country> addCountry(Country country) throws DataAccessException {
        Result<Country> result = new Result<>();

        //Make sure country id is not set.
        if (country != null && country.getId() > 0) {
            result.addErrorMessage("Country `id` should not be set.");
        }

        //Create a validator object - validations are defined on the model
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<Country>> violations = validator.validate(country);

        //If Invalid, add violations as error messages to result object and return result object
        if (!violations.isEmpty()) {
            for (ConstraintViolation<Country> violation : violations) {
                result.addErrorMessage(violation.getMessage());
            }
            return result;
        }
        //If you have not added error messages to result object, then it is success. Perform update. If addCountry works,
        //add the country as a payload to the result object.
        if (result.isSuccess()) {
            country = countryRepository.addCountry(country);
            result.setPayload(country);
        }
        return result;
    }

    //Update Country
    public Result<Country> updateCountry(Country country) throws DataAccessException {
        Result<Country> result = new Result<>();

        if (country.getId() <= 0) {
            result.addErrorMessage("Country `id` is required.");
        }
        //Create a validator object
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<Country>> violations = validator.validate(country);
        //If Invalid, add violations as error messages to result object and return result object
        if (!violations.isEmpty()) {
            for (ConstraintViolation<Country> violation : violations) {
                result.addErrorMessage(violation.getMessage());
            }
            return result;
        }
        //If you have not added error messages to result object, then it is success. Perform update. If update returns true,
        //add solar panel to the result object. If returns false, return error message.
        if (result.isSuccess()) {
            if (countryRepository.updateCountry(country)) {
                result.setPayload(country);
            } else {
                result.addErrorMessage(String.format("Country id %s was not found.", country.getId()));
                result.setNotFound();
            }
        }
        return result;
    }

    //Delete Country
    public Result<Country> deleteById(int countryId) throws DataAccessException {
        Result<Country> result = new Result<>();
        if (!countryRepository.deleteCountryById(countryId)) {
            result.addErrorMessage(String.format("Country id %s was not found.", countryId));
            result.setNotFound();
        }
        return result;
    }

    //Find All Countries associated with one User ID (Get Bucket List)
    public List<Country> findAllCountriesByUserId(int userId){
        return countryRepository.findallCountriesByUserId(userId);
    }

    //Is Country on user bucket list?
    public boolean findOneCountryByUserId(int attractionId, int userId){
        return countryRepository.findOneCountryByUserId(attractionId, userId);
    }

    //Add Country Associated with a User ID (Add to Bucket List)
    public Result<Void> addToUserCountryList(int appUserId, int countyId){
        Result<Void> result = new Result<>();
        if(!countryRepository.addToUserCountryList(appUserId, countyId)){
            result.addErrorMessage(("Transaction Failed At the Repository Level"));
        }
        return result;
    }

    //Delete Record From App_User_City (Delete from Bucket List)
    public Result<Void> deleteFromUserCountryList(int appUserId, int countryId){
        Result<Void> result = new Result<>();
        if (!countryRepository.deleteFromUserCountryList(appUserId, countryId)) {
            result.addErrorMessage("Transaction Failed At the Repository Level");
            result.setNotFound();
        }
        return result;
    }

}
