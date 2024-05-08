package org.example.domain;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.example.data.repositories.AttractionRepository;
import org.example.models.Attraction;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class AttractionService {

    //Dependencies
    private final AttractionRepository attractionRepository;

    //Constructor
    public AttractionService(AttractionRepository attractionRepository) {
        this.attractionRepository = attractionRepository;
    }


    //Find All by Country ID
    public List<Attraction> findAttractionsByCountryId(int countryId) {
        return attractionRepository.findAttractionsByCountryId(countryId);
    }

    //Find By ID
    public Attraction findAttractionByID(int attractionId) {
        return attractionRepository.findAttractionByID(attractionId);
    }

    //Add Attraction
    public Result<Attraction> addAttraction(Attraction attraction) throws DataAccessException {
        Result<Attraction> result = new Result<>();

        //Make sure Attraction id is not set.
        if (attraction != null && attraction.getId() > 0) {
            result.addErrorMessage("Attraction `id` should not be set.");
        }

        //Create a validator object - validations are defined on the model
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<Attraction>> violations = validator.validate(attraction);

        //If Invalid, add violations as error messages to result object and return result object
        if (!violations.isEmpty()) {
            for (ConstraintViolation<Attraction> violation : violations) {
                result.addErrorMessage(violation.getMessage());
            }
            return result;
        }
        //If you have not added error messages to result object, then it is success. Perform update. If addAttraction works,
        //add the attraction as a payload to the result object.
        if (result.isSuccess()) {
            attraction = attractionRepository.addAttraction(attraction);
            result.setPayload(attraction);
        }
        return result;
    }

    //Update Attraction
    public Result<Attraction> updateAttraction(Attraction attraction) throws DataAccessException {
        Result<Attraction> result = new Result<>();

        if (attraction.getId() <= 0) {
            result.addErrorMessage("Attraction `id` is required.");
        }
        //Create a validator object
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<Attraction>> violations = validator.validate(attraction);
        //If Invalid, add violations as error messages to result object and return result object
        if (!violations.isEmpty()) {
            for (ConstraintViolation<Attraction> violation : violations) {
                result.addErrorMessage(violation.getMessage());
            }
            return result;
        }
        //If you have not added error messages to result object, then it is success. Perform update. If update returns true,
        //add solar panel to the result object. If returns false, return error message.
        if (result.isSuccess()) {
            if (attractionRepository.updateAttraction(attraction)) {
                result.setPayload(attraction);
            } else {
                result.addErrorMessage(String.format("Attraction id %s was not found.", attraction.getId()));
                result.setNotFound();
            }
        }
        return result;
    }

    //Delete Attraction
    public Result<Attraction> deleteById(int attractionId) throws DataAccessException {
        Result<Attraction> result = new Result<>();
        if (!attractionRepository.deleteAttractionById(attractionId)) {
            result.addErrorMessage(String.format("Attraction id %s was not found.", attractionId));
            result.setNotFound();
        }
        return result;
    }


    //Find All Attractions associated with one User ID (Get Bucket List)
    public List<Attraction> findAllAttractionsByUserId(int userId){
        return attractionRepository.findAllAttractionsByUserId(userId);
    }

    //Add Attraction Associated with a User ID (Add to Bucket List)
    public Result<Void> addToUserAttractionList(int appUserId, int attractionId){
        Result<Void> result = new Result<>();
        if(!attractionRepository.addToUserAttractionList(appUserId, attractionId)){
            result.addErrorMessage(("Transaction Failed At the Repository Level"));
        }
        return result;
    }



    //Delete Record From App_User_Attraction (Delete from Bucket List)
    public Result<Void> deleteFromUserAttractionList(int appUserId, int attractionId){
        Result<Void> result = new Result<>();
        if (!attractionRepository.deleteFromUserAttractionList(appUserId, attractionId)) {
            result.addErrorMessage("Transaction Failed At the Repository Level");
            result.setNotFound();
        }
        return result;
    }


}
