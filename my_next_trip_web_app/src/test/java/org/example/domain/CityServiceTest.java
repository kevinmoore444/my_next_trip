package org.example.domain;

import org.example.data.repositories.CityRepository;
import org.example.models.City;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class CityServiceTest {

    @Autowired
    CityService service;

    @MockBean
    CityRepository repository;


    @Test
    void shouldAddCity() {
        //Build Valid Test City
        City city = new City();
        city.setName("Test");
        city.setImage("Test");
        city.setDescription("Test");
        city.setCountryId(1);

        when(repository.addCity(city)).thenReturn(city);

        Result<City> actual = service.addCity(city);
        assertEquals(ResultType.SUCCESS, actual.getType());
        assertEquals(city, actual.getPayload());
    }

    @Test
    void shouldNotAddCityWhenInvalid(){
        //Build InValid  City - missing image
        City city = new City();
        city.setName("Test");
        city.setDescription("Test");
        city.setCountryId(1);

        Result<City> actual = service.addCity(city);
        assertEquals(ResultType.INVALID, actual.getType());
        assertNotEquals(city, actual.getPayload());
    }


    @Test
    void shouldUpdateCity() {
        //Build Valid Test City
        City city = new City();
        city.setId(1);
        city.setName("Test");
        city.setImage("Test");
        city.setDescription("Test");
        city.setCountryId(1);

        when(repository.updateCity(city)).thenReturn(true);

        Result<City> actual = service.updateCity(city);
        assertEquals(ResultType.SUCCESS, actual.getType());
        assertEquals(city, actual.getPayload());

    }

    @Test
    void shouldNotUpdateMissingID(){
        //Build Invalid Test City - id is missing
        City city = new City();
        city.setName("Test");
        city.setDescription("Test");
        city.setCountryId(1);

        Result<City> actual = service.updateCity(city);
        assertEquals(ResultType.INVALID, actual.getType());
        assertNotEquals(city, actual.getPayload());
    }
}