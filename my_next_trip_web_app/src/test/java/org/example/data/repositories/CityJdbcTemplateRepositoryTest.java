package org.example.data.repositories;

import org.example.models.City;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class CityJdbcTemplateRepositoryTest {

    @Autowired
    CityJdbcTemplateRepository repository;

    @Autowired
    KnownGoodState knownGoodState;

    @BeforeEach
    void setup() {
        knownGoodState.set();
    }

    @Test
    void shouldFindCitiesByCountryId() {
        List<City> expected = repository.findCitiesByCountryId(1);
        assertTrue(expected.size() == 2);
    }

    @Test
    void findCitiesByID() {
        City expected = repository.findCityByID(1);
        assertNotNull(expected);
        assertTrue(expected.getId() == 1);
    }

    @Test
    void shouldAddCity() {
        City expected = new City();
        expected.setName("Test");
        expected.setDescription("Test");
        expected.setImage("Test");
        expected.setCountryId(1);

        City actual = repository.addCity(expected);
        assertEquals(actual, expected);
    }


    @Test
    void shouldUpdateCity() {
        City city = new City();
        city.setId(1);
        city.setName("Test");
        city.setImage("Test");
        city.setDescription("Test");
        city.setCountryId(1);

        Boolean expected = repository.updateCity(city);
        assertTrue(expected);
    }

    @Test
    void deleteCityById() {
        Boolean expected = repository.deleteCityById(1);
        assertTrue(expected);
    }
}