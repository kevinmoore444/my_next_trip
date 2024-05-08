package org.example.data.repositories;

import org.example.models.CostOfLiving;

import java.util.List;

public interface CostOfLivingRepository {
    //Find All
    List<CostOfLiving> findCostOfLivingOptions();
}
