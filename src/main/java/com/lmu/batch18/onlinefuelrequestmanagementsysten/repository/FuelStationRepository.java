package com.lmu.batch18.onlinefuelrequestmanagementsysten.repository;

import com.lmu.batch18.onlinefuelrequestmanagementsysten.models.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface FuelStationRepository extends JpaRepository<FuelStation, Integer> {
//    FuelStation findByUserEquals(User user);

    FuelStation findByDisplayName(String displayName);

    FuelStation findByManagerFirstName(String userName);

    @Query(value = "select count(*) from fuel_station", nativeQuery = true)
    Integer getAllRegisteredFuelStationCount();

    @Query(value = "select SUM(actual_quata) as consumedLiterCount from fuel_request where fuel_station_id=?1 and fuel_type=?2 group by fuel_type", nativeQuery = true)
    Double getRemainingFuelCount(int id, String type);

    @Query(value = "select petrol_stock from fuel_station where id=?1", nativeQuery = true)
    Double getPetrolOrDiselStock(int id, String type);

    @Transactional
    @Modifying
    @Query(
            value = "UPDATE fuel_station\n" +
                    "SET petrol_stock = ?1 where id=?2",
            nativeQuery = true
    )
    void updatePetrolStock(Double actualQuata, FuelStation fuelStation);
    @Transactional
    @Modifying
    @Query(
            value = "UPDATE fuel_station\n" +
                    "SET diesel_stock = ?1 where id=?2",
            nativeQuery = true
    )
    void updateDieselStock(Double finalValue, FuelStation fuelStation);
}


