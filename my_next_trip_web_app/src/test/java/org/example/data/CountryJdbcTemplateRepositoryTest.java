package org.example.data;

import org.example.data.repositories.CountryJdbcTemplateRepository;
import org.example.models.CostOfLiving;
import org.example.models.Country;
import org.example.models.Region;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class CountryJdbcTemplateRepositoryTest {

    @Autowired
    CountryJdbcTemplateRepository repository;

    @Autowired
    KnownGoodState knownGoodState;

    @BeforeEach
    void setup() {
        knownGoodState.set();
    }

    @Test
    void shouldFindAllCountries() {
        List<Country> countries = repository.findAllCountries();
        assertNotNull(countries);
        assertTrue(countries.size() >= 2 && countries.size() <= 4);
    }

    @Test
    void shouldFindCountryById() {
        Country expected = repository.findCountryByID(1);
        assertNotNull(expected);
        assertTrue(expected.getName().equals("France"));
    }
    @Test
    void shouldAddCountry() {
        Region region = new Region();
        region.setId(1);

        CostOfLiving costOfLiving = new CostOfLiving();
        costOfLiving.setId(1);

        Country expected = new Country();
        expected.setRegion(region);
        expected.setCostOfLiving(costOfLiving);
        expected.setDescription("Test");
        expected.setImage("Test");
        expected.setName("Test");

        Country actual = repository.addCountry(expected);
        assertEquals(actual, expected);
    }

    @Test
    void shouldUpdateCountry() {
        Region region = new Region();
        region.setId(1);

        CostOfLiving costOfLiving = new CostOfLiving();
        costOfLiving.setId(1);

        Country country = new Country();
        country.setId(2);
        country.setRegion(region);
        country.setCostOfLiving(costOfLiving);
        country.setDescription("Test");
        country.setImage("Test");
        country.setName("Test");

        Boolean expected = repository.updateCountry(country);
        assertTrue(expected);
    }

    @Test
    void shouldDeleteCountryById() {
        Boolean expected = repository.deleteCountryById(1);
        assertTrue(expected);
    }
}
