package com.lmu.batch18.onlinefuelrequestmanagementsysten.service;

import com.lmu.batch18.onlinefuelrequestmanagementsysten.dto.FuelRequestByFuelStationDTO;
import com.lmu.batch18.onlinefuelrequestmanagementsysten.models.FuelRequestByFuelStation;
import com.lmu.batch18.onlinefuelrequestmanagementsysten.util.CommonResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface FuelRequestByFuelStationService {

    ResponseEntity<CommonResponse> saveFuelRequestByFuelStation(FuelRequestByFuelStation fuelRequestByFuelStationDTO);

    ResponseEntity<CommonResponse> getFuelRequestsById(int fuelRequestId);

    ResponseEntity<CommonResponse> getFuelRequestsByFuelStationId(int fuelStationId);

    List<FuelRequestByFuelStation> findAllFuelRequstByFuelStation();
}
