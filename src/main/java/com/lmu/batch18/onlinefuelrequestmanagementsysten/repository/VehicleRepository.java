package com.lmu.batch18.onlinefuelrequestmanagementsysten.repository;

import com.lmu.batch18.onlinefuelrequestmanagementsysten.models.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.List;


@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, Integer> {
    Vehicle findByVehicleNumber(String vehicleNumber);

    @Query(
            value = "select * from vehicle where user_id=?1 order by CASE WHEN updated_date > created_date THEN updated_date\n" +
                    "        ELSE created_date\n" +
                    "   END  desc ",
            nativeQuery = true
    )
    @Transactional
    List<Vehicle> findByUserId(String userId);

    Vehicle findByChassisNumber(String chassisNumber);

    @Query(value = "select count(*) from vehicle", nativeQuery = true)
    Integer allRegisteredVehiclesCount();

}
