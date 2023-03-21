package com.lmu.batch18.onlinefuelrequestmanagementsysten.repository;

import com.lmu.batch18.onlinefuelrequestmanagementsysten.models.FuelRequestByFuelStation;
import com.lmu.batch18.onlinefuelrequestmanagementsysten.models.FuelRequestHistory;
import com.lmu.batch18.onlinefuelrequestmanagementsysten.models.FuelStation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@EnableJpaRepositories
public interface FuelRequestByFuelStationRepository extends JpaRepository<FuelRequestByFuelStation,Integer> {
    List<FuelRequestByFuelStation> findAllByFuelStation(FuelStation fuelStation);

    @Transactional
    @Modifying
    @Query(
            value = "UPDATE fuel_request_by_fuel_station\n" +
                    "SET approval_state = true WHERE id=?1",
            nativeQuery = true
    )
    void updateApproveById(int fuelRequestId);

    @Transactional
    @Modifying
    @Query(
            value = "UPDATE fuel_request_by_fuel_station\n" +
                    "SET reject_state = true WHERE id=?1",
            nativeQuery = true
    )
    void updateRejectById(int fuelRequestId);
}
