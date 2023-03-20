package com.lmu.batch18.onlinefuelrequestmanagementsysten.repository;

import com.lmu.batch18.onlinefuelrequestmanagementsysten.models.FuelRequestByFuelStation;
import com.lmu.batch18.onlinefuelrequestmanagementsysten.models.FuelRequestHistory;
import com.lmu.batch18.onlinefuelrequestmanagementsysten.models.FuelStation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@EnableJpaRepositories
public interface FuelRequestByFuelStationRepository extends JpaRepository<FuelRequestByFuelStation,Integer> {
    List<FuelRequestByFuelStation> findAllByFuelStation(FuelStation fuelStation);
}
