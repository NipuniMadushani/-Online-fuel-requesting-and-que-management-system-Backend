package com.lmu.batch18.onlinefuelrequestmanagementsysten.repository;

import com.lmu.batch18.onlinefuelrequestmanagementsysten.models.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FuelStationRepository  extends JpaRepository<FuelStation, Integer> {
    FuelStation findByUserEquals(User user);
}


