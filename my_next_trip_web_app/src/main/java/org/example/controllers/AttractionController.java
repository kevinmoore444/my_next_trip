package org.example.controllers;

import jakarta.validation.Valid;
import org.example.domain.AttractionService;
import org.example.domain.Result;
import org.example.domain.ResultType;
import org.example.models.Attraction;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/attraction")
@CrossOrigin
public class AttractionController {

    //Dependencies
    private final AttractionService service;


    //Constructor
    public AttractionController(AttractionService service) {
        this.service = service;
    }


    //API Endpoints

    @GetMapping("/list/{countryId}")
    public List<Attraction> findAttractionsByCountryId(@PathVariable int countryId) throws DataAccessException {
        return service.findAttractionsByCountryId(countryId);
    }

    @GetMapping("/{attractionId}")
    public ResponseEntity<Attraction> findAttractionById(@PathVariable int attractionId) throws DataAccessException {
        Attraction attraction = service.findAttractionByID(attractionId);
        if (attraction == null) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(attraction, HttpStatus.OK);
    }


    @PostMapping("/user")
    public ResponseEntity<?> createAttraction(@RequestBody @Valid Attraction attraction, BindingResult bindingResult) throws DataAccessException {
        //Controller level validation:
        if (bindingResult.hasErrors()) {
            // BindResult.getAllErrors() returns violations wrapped in Spring ObjectError instances.
            return new ResponseEntity<>(bindingResult.getAllErrors(), HttpStatus.BAD_REQUEST);
        }

        //If it passes validation, call service to create solar panel as usual
        Result<Attraction> result = service.addAttraction(attraction);
        if (!result.isSuccess()) {
            return new ResponseEntity<>(result.getErrorMessages(), HttpStatus.BAD_REQUEST); // 400
        }
        return new ResponseEntity<>(result.getPayload(), HttpStatus.CREATED); // 201
    }


    @PutMapping("/user/{attractionId}")
    public ResponseEntity<?> updateAttraction(@PathVariable int attractionId, @RequestBody @Valid Attraction attraction, BindingResult bindingResult) throws DataAccessException {
        if (attractionId != attraction.getId()) {
            return new ResponseEntity<>(HttpStatus.CONFLICT); // 409 Error
        }
        //Controller level validation:
        if (bindingResult.hasErrors()) {
            // BindResult.getAllErrors() returns violations wrapped in Spring ObjectError instances.
            return new ResponseEntity<>(bindingResult.getAllErrors(), HttpStatus.BAD_REQUEST);
        }
        //If it passes validation, call service to update solar panel as usual
        Result<Attraction> result = service.updateAttraction(attraction);
        if (!result.isSuccess()) {
            if (result.getType() == ResultType.NOT_FOUND) {
                //If ID Not Found
                return new ResponseEntity<>(HttpStatus.NOT_FOUND); // 404 Error
            } else {
                //If
                return new ResponseEntity<>(result.getErrorMessages(), HttpStatus.BAD_REQUEST); // 400 Error
            }
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT); // 204 Error
    }

    @DeleteMapping("/user/{attractionId}")
    public ResponseEntity<Void> deleteAttraction(@PathVariable int attractionId) throws DataAccessException {
        Result<Attraction> result = service.deleteById(attractionId);
        if (result.getType() == ResultType.NOT_FOUND) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // 404 Error
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT); // 204 Error
    }



    //Find All Attractions associated with one User ID (Get Bucket List)
    @GetMapping("/user-list/{userId}")
    public List<Attraction> findAllAttractionsByUserId(@PathVariable int userId) throws DataAccessException {
        return service.findAllAttractionsByUserId(userId);
    }

    //Add Attraction Associated with a User ID (Add to Bucket List)
    @PostMapping("/{attractionId}/{appUserId}")
    public ResponseEntity<?> addToUserAttractionList(@PathVariable int attractionId, @PathVariable int appUserId) throws DataAccessException {

        Result<?> result = service.addToUserAttractionList(appUserId, attractionId);
        if (!result.isSuccess()) {
            return new ResponseEntity<>(result.getErrorMessages(), HttpStatus.BAD_REQUEST); // 400
        }
        return new ResponseEntity<>(HttpStatus.CREATED); // 201
    }

    //Delete Record From App_User_Attraction (Delete from Bucket List)
    @DeleteMapping("/{attractionId}/{appUserId}")
    public ResponseEntity<Void> deleteFromUserAttractionList(@PathVariable int attractionId, @PathVariable int appUserId) throws DataAccessException {
        Result<Void> result = service.deleteFromUserAttractionList(appUserId, attractionId);
        if (result.getType() == ResultType.NOT_FOUND) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // 404 Error
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT); // 204
    }

}
