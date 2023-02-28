package com.lmu.batch18.onlinefuelrequestmanagementsysten.service;

import com.lmu.batch18.onlinefuelrequestmanagementsysten.dto.FuelStationDTO;
import com.lmu.batch18.onlinefuelrequestmanagementsysten.util.CommonResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface FuelStationService {
    ResponseEntity<CommonResponse> saveFuelStation(FuelStationDTO fuelStationDTO);

    FuelStationDTO getFuelStationByUserId(int userId);

    List<FuelStationDTO> getALlFuelStations();

    ResponseEntity<CommonResponse> updateFuelStation(int id, FuelStationDTO fuelStationDTO);

    ResponseEntity<CommonResponse> getFuelStationById(int id);

    ResponseEntity<CommonResponse> getFillingStationDetailsManagerWise(String userName);

    Integer getAllRegisteredFuelStationCount();
}
