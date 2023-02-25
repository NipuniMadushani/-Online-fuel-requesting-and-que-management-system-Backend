package com.lmu.batch18.onlinefuelrequestmanagementsysten.repository;

import com.lmu.batch18.onlinefuelrequestmanagementsysten.models.Customer;
import com.lmu.batch18.onlinefuelrequestmanagementsysten.models.FuelRequest;
import com.lmu.batch18.onlinefuelrequestmanagementsysten.models.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface FuelRequestRepository  extends JpaRepository<FuelRequest, Integer> {

//    List<FuelRequest> findByCustomerEquals(Customer u);
    List<FuelRequest> findAllByVehicleEquals(Vehicle vehicle);
    @Query(
            value = "select * from fuel_request where user_id=?1 order by CASE WHEN updated_date > created_date THEN updated_date\n" +
                    "        ELSE created_date\n" +
                    "   END  desc ",
            nativeQuery = true
    )
    @Transactional
    List<FuelRequest> findByUserId(int customerId);

    @Query(
            value = "select * from fuel_request where id=?1",
            nativeQuery = true
    )
    @Transactional
    List<FuelRequest> findByRequestId(int requestId);

}
