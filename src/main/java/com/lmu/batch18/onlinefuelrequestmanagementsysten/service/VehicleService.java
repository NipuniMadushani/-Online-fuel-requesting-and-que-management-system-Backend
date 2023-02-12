package com.lmu.batch18.onlinefuelrequestmanagementsysten.service;

import com.lmu.batch18.onlinefuelrequestmanagementsysten.dto.VehicleDTO;
import com.lmu.batch18.onlinefuelrequestmanagementsysten.util.CommonResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface VehicleService {
    ResponseEntity<CommonResponse> registerVehicle(VehicleDTO vehicleDTO);

    List<VehicleDTO> getAllRegisteredVehicles();

    ResponseEntity<CommonResponse> getVehicleById(Long id);

    ResponseEntity<CommonResponse> updateVehicle(Long id, VehicleDTO vehicleDTO);

    ResponseEntity<CommonResponse> checkVehicleByVehicleNumber(String vehicleNumber);
}
