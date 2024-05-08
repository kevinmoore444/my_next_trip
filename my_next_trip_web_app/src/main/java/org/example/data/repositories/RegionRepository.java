package org.example.data.repositories;

import org.example.models.Region;

import java.util.List;

public interface RegionRepository {
    //Find All
    List<Region> findAllRegionOptions();
}
