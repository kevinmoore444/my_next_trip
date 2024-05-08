package org.example.controllers;

import jakarta.validation.Valid;
import org.example.domain.CityService;
import org.example.domain.Result;
import org.example.domain.ResultType;
import org.example.models.City;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/city")
@CrossOrigin
public class CityController {

    //Dependencies
    private final CityService service;


    //Constructor
    public CityController(CityService service) {
        this.service = service;
    }

    //API Endpoints

    @GetMapping ("/list/{countryId}")
    public List<City> findCitiesByCountryId(@PathVariable int countryId) throws DataAccessException {
        return service.findCitiesByCountryId(countryId);
    }

    @GetMapping("/{cityId}")
    public ResponseEntity<City> findCityById(@PathVariable int cityId) throws DataAccessException {
        City city = service.findCityByID(cityId);
        if (city == null) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(city, HttpStatus.OK);
    }


    @PostMapping("/user")
    public ResponseEntity<?> createCity(@RequestBody @Valid City city, BindingResult bindingResult) throws DataAccessException {
        //Controller level validation:
        if (bindingResult.hasErrors()) {
            // BindResult.getAllErrors() returns violations wrapped in Spring ObjectError instances.
            return new ResponseEntity<>(bindingResult.getAllErrors(), HttpStatus.BAD_REQUEST);
        }

        //If it passes validation, call service to create solar panel as usual
        Result<City> result = service.addCity(city);
        if (!result.isSuccess()) {
            return new ResponseEntity<>(result.getErrorMessages(), HttpStatus.BAD_REQUEST); // 400
        }
        return new ResponseEntity<>(result.getPayload(), HttpStatus.CREATED); // 201
    }


    @PutMapping("/user/{cityId}")
    public ResponseEntity<?> updateCity(@PathVariable int cityId, @RequestBody @Valid City city, BindingResult bindingResult) throws DataAccessException {
        if (cityId != city.getId()) {
            return new ResponseEntity<>(HttpStatus.CONFLICT); // 409 Error
        }
        //Controller level validation:
        if (bindingResult.hasErrors()) {
            // BindResult.getAllErrors() returns violations wrapped in Spring ObjectError instances.
            return new ResponseEntity<>(bindingResult.getAllErrors(), HttpStatus.BAD_REQUEST);
        }
        //If it passes validation, call service to update solar panel as usual
        Result<City> result = service.updateCity(city);
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

    @DeleteMapping("/user/{cityId}")
    public ResponseEntity<Void> deleteCity(@PathVariable int cityId) throws DataAccessException {
        Result<City> result = service.deleteById(cityId);
        if (result.getType() == ResultType.NOT_FOUND) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // 404 Error
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT); // 204 Error
    }


    //Find All Cities associated with one User ID (Get Bucket List)
    @GetMapping("/user-list/{userId}")
    public List<City> findAllCitiesByUserId(@PathVariable int userId) throws DataAccessException {
        return service.findAllCitiesByUserId(userId);
    }

    //Is City on user bucket list?
    @GetMapping("/user-list/{cityId}/{userId}")
    public boolean findOneCityByUserId(@PathVariable int cityId, @PathVariable int userId) throws DataAccessException {
        return service.findOneCityByUserId(cityId, userId);
    }

    //Add Attraction Associated with a User ID (Add to Bucket List)
    @PostMapping("/user-list/{cityId}/{appUserId}")
    public ResponseEntity<?> addToUserCityList(@PathVariable int cityId, @PathVariable int appUserId) throws DataAccessException {

        Result<?> result = service.addToUserCityList(appUserId, cityId);
        if (!result.isSuccess()) {
            return new ResponseEntity<>(result.getErrorMessages(), HttpStatus.BAD_REQUEST); // 400
        }
        return new ResponseEntity<>(HttpStatus.CREATED); // 201
    }

    //Delete Record From App_User_City (Delete from Bucket List)
    @DeleteMapping("/user-list/{cityId}/{appUserId}")
    public ResponseEntity<Void> deleteFromUserCityList(@PathVariable int cityId, @PathVariable int appUserId) throws DataAccessException {
        Result<Void> result = service.deleteFromUserCityList(appUserId, cityId);
        if (result.getType() == ResultType.NOT_FOUND) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // 404 Error
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT); // 204
    }


}
