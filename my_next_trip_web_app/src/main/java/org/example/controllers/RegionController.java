package org.example.controllers;


import org.example.domain.RegionService;
import org.example.models.Region;
import org.springframework.dao.DataAccessException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/region")
@CrossOrigin
public class RegionController {
    //Dependencies
    private final RegionService service;

    //Constructor
    public RegionController(RegionService service) {
        this.service = service;
    }

    //Find All
    @GetMapping
    public List<Region> findAllRegionOptions() throws DataAccessException {
        return service.findAllRegionOptions();
    }
}
