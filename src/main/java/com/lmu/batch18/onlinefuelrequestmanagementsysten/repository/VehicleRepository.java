package com.lmu.batch18.onlinefuelrequestmanagementsysten.repository;

import com.lmu.batch18.onlinefuelrequestmanagementsysten.models.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import javax.transaction.Transactional;
import java.util.List;


@Repository
public interface VehicleRepository extends JpaRepository<Vehicle,Long> {
    Vehicle findByVehicleNumber(String vehicleNumber);

    @Query(
            value = "select * from vehicle order by CASE WHEN updated_date > created_date THEN updated_date\n" +
                    "        ELSE created_date\n" +
                    "   END  desc",
            nativeQuery = true
    )
    @Transactional
    List<Vehicle> findAllDetails();
}
