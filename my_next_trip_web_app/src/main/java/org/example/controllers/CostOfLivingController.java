package org.example.controllers;


import org.example.domain.CostOfLivingService;
import org.example.models.CostOfLiving;
import org.springframework.dao.DataAccessException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/costofliving")
@CrossOrigin
public class CostOfLivingController {

    //Dependencies
    private final CostOfLivingService service;

    //Constructor
    public CostOfLivingController(CostOfLivingService service) {
        this.service = service;
    }

    //Find All
    @GetMapping
    public List<CostOfLiving> findAllCostOfLivingOptions() throws DataAccessException {
        return service.findAllCostOfLivingOptions();
    }

}
