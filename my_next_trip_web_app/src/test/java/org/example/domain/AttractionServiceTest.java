package org.example.domain;

import org.example.data.repositories.AttractionRepository;
import org.example.models.Attraction;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class AttractionServiceTest {

    @Autowired
    AttractionService service;

    @MockBean
    AttractionRepository repository;


    @Test
    void shouldAddAttraction() {
        //Build Valid Test Attraction
        Attraction attraction = new Attraction();
        attraction.setName("Test");
        attraction.setImage("Test");
        attraction.setDescription("Test");
        attraction.setCountryId(1);

        when(repository.addAttraction(attraction)).thenReturn(attraction);

        Result<Attraction> actual = service.addAttraction(attraction);
        assertEquals(ResultType.SUCCESS, actual.getType());
        assertEquals(attraction, actual.getPayload());
    }

    @Test
    void shouldNotAddAttractionWhenInvalid(){
        //Build InValid Test Attraction - Missing Name
        Attraction attraction = new Attraction();
        attraction.setImage("Test");
        attraction.setDescription("Test");
        attraction.setCountryId(1);

        Result<Attraction> actual = service.addAttraction(attraction);
        assertEquals(ResultType.INVALID, actual.getType());
        assertNotEquals(attraction, actual.getPayload());
    }


    @Test
    void shouldUpdateCity() {
        //Build Valid Test City
        Attraction attraction = new Attraction();
        attraction.setId(1);
        attraction.setName("Test");
        attraction.setImage("Test");
        attraction.setDescription("Test");
        attraction.setCountryId(1);

        when(repository.updateAttraction(attraction)).thenReturn(true);

        Result<Attraction> actual = service.updateAttraction(attraction);
        assertEquals(ResultType.SUCCESS, actual.getType());
        assertEquals(attraction, actual.getPayload());

    }

    @Test
    void shouldNotUpdateMissingID(){
        //Build Invalid Test Attraction - id is missing
        Attraction attraction = new Attraction();
        attraction.setName("Test");
        attraction.setDescription("Test");
        attraction.setImage("Test");
        attraction.setCountryId(1);

        Result<Attraction> actual = service.updateAttraction(attraction);
        assertEquals(ResultType.INVALID, actual.getType());
        assertNotEquals(attraction, actual.getPayload());
    }
}