package com.lmu.batch18.onlinefuelrequestmanagementsysten.repository;

import com.lmu.batch18.onlinefuelrequestmanagementsysten.models.Customer;
import com.lmu.batch18.onlinefuelrequestmanagementsysten.models.FuelRequest;
import com.lmu.batch18.onlinefuelrequestmanagementsysten.models.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FuelRequestRepository  extends JpaRepository<FuelRequest, Integer> {

    List<FuelRequest> findByCustomerEquals(Customer u);
    List<FuelRequest> findAllByVehicleEquals(Vehicle vehicle);
}
