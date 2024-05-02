package org.example.domain;

import org.example.data.repositories.CountryRepository;
import org.example.models.CostOfLiving;
import org.example.models.Country;
import org.example.models.Region;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.Mockito.when;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class CountryServiceTest {

    @Autowired
    CountryService service;

    @MockBean
    CountryRepository repository;


    @Test
    void shouldAddCountry() {
        //Build Valid Test Country
        Region region = new Region();
        region.setId(1);

        CostOfLiving costOfLiving = new CostOfLiving();
        costOfLiving.setId(1);

        Country country = new Country();
        country.setRegion(region);
        country.setCostOfLiving(costOfLiving);
        country.setDescription("Test");
        country.setImage("Test");
        country.setName("Test");


        when(repository.addCountry(country)).thenReturn(country);

        Result<Country> actual = service.addCountry(country);
        assertEquals(ResultType.SUCCESS, actual.getType());
        assertEquals(country, actual.getPayload());
    }

    @Test
    void shouldNotAddWhenInvalid(){
        //Build Invalid Test Country - description is null
        Region region = new Region();
        region.setId(1);

        CostOfLiving costOfLiving = new CostOfLiving();
        costOfLiving.setId(1);

        Country country = new Country();
        country.setRegion(region);
        country.setCostOfLiving(costOfLiving);
        country.setImage("Test");
        country.setName("Test");

        Result<Country> actual = service.addCountry(country);
        assertEquals(ResultType.INVALID, actual.getType());
        assertNotEquals(country, actual.getPayload());

    }


    @Test
    void shouldUpdateCountry() {
        //Build Valid Test Country for updating
        Region region = new Region();
        region.setId(1);

        CostOfLiving costOfLiving = new CostOfLiving();
        costOfLiving.setId(1);

        Country country = new Country();
        country.setId(1);
        country.setRegion(region);
        country.setCostOfLiving(costOfLiving);
        country.setDescription("Test");
        country.setImage("Test");
        country.setName("Test");

        when(repository.updateCountry(country)).thenReturn(true);

        Result<Country> actual = service.updateCountry(country);
        assertEquals(ResultType.SUCCESS, actual.getType());
        assertEquals(country, actual.getPayload());


    }

    @Test
    void shouldNotUpdateMissing(){
        //Build Invalid Test Country - id is missing
        Region region = new Region();

        CostOfLiving costOfLiving = new CostOfLiving();
        costOfLiving.setId(1);

        Country country = new Country();
        country.setRegion(region);
        country.setCostOfLiving(costOfLiving);
        country.setImage("Test");
        country.setDescription("Test");
        country.setName("Test");

        Result<Country> actual = service.updateCountry(country);
        assertEquals(ResultType.INVALID, actual.getType());
        assertNotEquals(country, actual.getPayload());
    }

}