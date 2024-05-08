package org.example.domain;


import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.example.data.repositories.CityRepository;
import org.example.models.City;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class CityService {

    //Dependencies
    private final CityRepository cityRepository;

    //Constructor
    public CityService(CityRepository cityRepository) {
        this.cityRepository = cityRepository;
    }


    //Find All by Country ID
    public List<City> findCitiesByCountryId(int countryId) {
        return cityRepository.findCitiesByCountryId(countryId);
    }

    //Find By ID
    public City findCityByID(int cityId) {
        return cityRepository.findCityByID(cityId);
    }

    //Add City
    public Result<City> addCity(City city) throws DataAccessException {
        Result<City> result = new Result<>();

        //Make sure city id is not set.
        if (city != null && city.getId() > 0) {
            result.addErrorMessage("City `id` should not be set.");
        }

        //Create a validator object - validations are defined on the model
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<City>> violations = validator.validate(city);

        //If Invalid, add violations as error messages to result object and return result object
        if (!violations.isEmpty()) {
            for (ConstraintViolation<City> violation : violations) {
                result.addErrorMessage(violation.getMessage());
            }
            return result;
        }
        //If you have not added error messages to result object, then it is success. Perform update. If addCity works,
        //add the city as a payload to the result object.
        if (result.isSuccess()) {
            city = cityRepository.addCity(city);
            result.setPayload(city);
        }
        return result;
    }

    //Update City
    public Result<City> updateCity(City city) throws DataAccessException {
        Result<City> result = new Result<>();

        if (city.getId() <= 0) {
            result.addErrorMessage("City `id` is required.");
        }
        //Create a validator object
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<City>> violations = validator.validate(city);
        //If Invalid, add violations as error messages to result object and return result object
        if (!violations.isEmpty()) {
            for (ConstraintViolation<City> violation : violations) {
                result.addErrorMessage(violation.getMessage());
            }
            return result;
        }
        //If you have not added error messages to result object, then it is success. Perform update. If update returns true,
        //add solar panel to the result object. If returns false, return error message.
        if (result.isSuccess()) {
            if (cityRepository.updateCity(city)) {
                result.setPayload(city);
            } else {
                result.addErrorMessage(String.format("City id %s was not found.", city.getId()));
                result.setNotFound();
            }
        }
        return result;
    }

    //Delete City
    public Result<City> deleteById(int cityId) throws DataAccessException {
        Result<City> result = new Result<>();
        if (!cityRepository.deleteCityById(cityId)) {
            result.addErrorMessage(String.format("City id %s was not found.", cityId));
            result.setNotFound();
        }
        return result;
    }


    //Find All Cities associated with one User ID (Get Bucket List)
    public List<City> findAllCitiesByUserId(int userId){
        return cityRepository.findAllCitiesByUserId(userId);
    }

    //Is City on user bucket list?
    public boolean findOneCityByUserId(int cityId, int userId){
        return cityRepository.findOneCityByUserId(cityId, userId);
    }

    //Add City Associated with a User ID (Add to Bucket List)
    public Result<Void> addToUserCityList(int appUserId, int cityId){
        Result<Void> result = new Result<>();
        if(!cityRepository.addToUserCityList(appUserId, cityId)){
            result.addErrorMessage(("Transaction Failed At the Repository Level"));
        }
        return result;
    }

    //Delete Record From App_User_City (Delete from Bucket List)
    public Result<Void> deleteFromUserCityList(int appUserId, int cityId){
        Result<Void> result = new Result<>();
        if (!cityRepository.deleteFromUserCityList(appUserId, cityId)) {
            result.addErrorMessage("Transaction Failed At the Repository Level");
            result.setNotFound();
        }
        return result;
    }











}
