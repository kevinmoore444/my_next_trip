package org.example.controllers;


import jakarta.validation.Valid;
import org.example.domain.CountryService;
import org.example.domain.Result;
import org.example.domain.ResultType;
import org.example.models.Country;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/country")
@CrossOrigin
public class CountryController {

    //Dependencies
    private final CountryService service;

    //Constructor
    public CountryController(CountryService service) {
        this.service = service;
    }

    
    //API Endpoints
    @GetMapping
    public List<Country> findAllCountries() throws DataAccessException {
        return service.findAllCountries();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Country> findCountryById(@PathVariable int id) throws DataAccessException {
        Country country = service.findById(id);
        if (country == null) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(country, HttpStatus.OK);
    }

    @PostMapping ("/user")
    public ResponseEntity<?> createCountry(@RequestBody @Valid Country country, BindingResult bindingResult) throws DataAccessException {
        //Controller level validation:
        if (bindingResult.hasErrors()) {
            // BindResult.getAllErrors() returns violations wrapped in Spring ObjectError instances.
            return new ResponseEntity<>(bindingResult.getAllErrors(), HttpStatus.BAD_REQUEST);
        }

        //If it passes validation, call service to create solar panel as usual
        Result<Country> result = service.addCountry(country);
        if (!result.isSuccess()) {
            return new ResponseEntity<>(result.getErrorMessages(), HttpStatus.BAD_REQUEST); // 400
        }
        return new ResponseEntity<>(result.getPayload(), HttpStatus.CREATED); // 201
    }

    @PutMapping("/user/{id}")
    public ResponseEntity<?> updateCountry(@PathVariable int id, @RequestBody @Valid Country country, BindingResult bindingResult) throws DataAccessException {
        if (id != country.getId()) {
            return new ResponseEntity<>(HttpStatus.CONFLICT); // 409 Error
        }
        //Controller level validation:
        if (bindingResult.hasErrors()) {
            // BindResult.getAllErrors() returns violations wrapped in Spring ObjectError instances.
            return new ResponseEntity<>(bindingResult.getAllErrors(), HttpStatus.BAD_REQUEST);
        }
        //If it passes validation, call service to update solar panel as usual
        Result<Country> result = service.updateCountry(country);
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

    @DeleteMapping("/user/{id}")
    public ResponseEntity<Void> deleteCountry(@PathVariable int id) throws DataAccessException {
        Result<Country> result = service.deleteById(id);
        if (result.getType() == ResultType.NOT_FOUND) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // 404 Error
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT); // 204 Error
    }


    //Find All Countries associated with one User ID (Get Bucket List)
    @GetMapping("/user-list/{userId}")
    public List<Country> findAllCountriesByUserId(@PathVariable int userId) throws DataAccessException {
        return service.findAllCountriesByUserId(userId);
    }

    //Add Country Associated with a User ID (Add to Bucket List)
    @PostMapping("/{countryId}/{appUserId}")
    public ResponseEntity<?> addToUserCountryList(@PathVariable int countryId, @PathVariable int appUserId) throws DataAccessException {

        Result<?> result = service.addToUserCountryList(appUserId, countryId);
        if (!result.isSuccess()) {
            return new ResponseEntity<>(result.getErrorMessages(), HttpStatus.BAD_REQUEST); // 400
        }
        return new ResponseEntity<>(HttpStatus.CREATED); // 201
    }

    //Delete Record From App_User_City (Delete from Bucket List)
    @DeleteMapping("/{countryId}/{appUserId}")
    public ResponseEntity<Void> deleteFromUserCityList(@PathVariable int countryId, @PathVariable int appUserId) throws DataAccessException {
        Result<Void> result = service.deleteFromUserCountryList(appUserId, countryId);
        if (result.getType() == ResultType.NOT_FOUND) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // 404 Error
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT); // 204
    }



}
