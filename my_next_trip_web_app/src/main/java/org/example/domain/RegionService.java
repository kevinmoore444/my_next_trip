package org.example.domain;


import org.example.data.repositories.RegionRepository;
import org.example.models.Region;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RegionService {

    //Dependencies
    private final RegionRepository regionRepository;


    //Constructor
    public RegionService(RegionRepository regionRepository) {
        this.regionRepository = regionRepository;
    }

    //Find All Region Options
    public List<Region> findAllRegionOptions() {
        return regionRepository.findAllRegionOptions();
    }





}
