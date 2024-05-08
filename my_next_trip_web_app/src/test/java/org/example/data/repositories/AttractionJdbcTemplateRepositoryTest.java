package org.example.data.repositories;

import org.example.models.Attraction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class AttractionJdbcTemplateRepositoryTest {

    @Autowired
    AttractionJdbcTemplateRepository repository;

    @Autowired
    KnownGoodState knownGoodState;

    @BeforeEach
    void setup() {
        knownGoodState.set();
    }


    @Test
    void shouldFindAttractionsByCountryId() {
        List<Attraction> expected = repository.findAttractionsByCountryId(1);
        assertTrue(expected.size() == 2);
    }

    @Test
    void findAttractionByID() {
        Attraction expected = repository.findAttractionByID(1);
        System.out.println(expected);
        assertNotNull(expected);
        assertTrue(expected.getId() == 1);
    }

    @Test
    void shouldAddAttraction() {
        Attraction expected = new Attraction();
        expected.setName("Test");
        expected.setDescription("Test");
        expected.setImage("Test");
        expected.setCountryId(1);

        Attraction actual = repository.addAttraction(expected);
        assertEquals(actual, expected);
    }


    @Test
    void shouldUpdateAttraction() {
        Attraction attraction = new Attraction();
        attraction.setId(1);
        attraction.setName("Test");
        attraction.setImage("Test");
        attraction.setDescription("Test");
        attraction.setCountryId(1);

        Boolean expected = repository.updateAttraction(attraction);
        assertTrue(expected);
    }

    @Test
    void deleteAttractionById() {
        Boolean expected = repository.deleteAttractionById(2);
        assertTrue(expected);
    }
}