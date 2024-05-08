package org.example.domain;


import org.example.data.repositories.CostOfLivingRepository;
import org.example.models.CostOfLiving;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CostOfLivingService {

    //Dependencies
    private final CostOfLivingRepository costOfLivingRepository;

    //Constructor
    public CostOfLivingService(CostOfLivingRepository costOfLivingRepository) {
        this.costOfLivingRepository = costOfLivingRepository;
    }


    //Find All Cost of Living Options
    public List<CostOfLiving> findAllCostOfLivingOptions() {
        return costOfLivingRepository.findCostOfLivingOptions();
    }

}
