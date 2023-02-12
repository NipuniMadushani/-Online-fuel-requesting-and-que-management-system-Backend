package com.lmu.batch18.onlinefuelrequestmanagementsysten.serviceImpl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lmu.batch18.onlinefuelrequestmanagementsysten.dto.VehicleDTO;
import com.lmu.batch18.onlinefuelrequestmanagementsysten.models.Vehicle;
import com.lmu.batch18.onlinefuelrequestmanagementsysten.repository.VehicleRepository;
import com.lmu.batch18.onlinefuelrequestmanagementsysten.service.VehicleService;
import com.lmu.batch18.onlinefuelrequestmanagementsysten.util.CommonConst;
import com.lmu.batch18.onlinefuelrequestmanagementsysten.util.CommonResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class VehicleServiceImpl implements VehicleService {
    @Autowired
    private VehicleRepository vehicleRepository;
    Map<String, Object> response = new HashMap<>();

    @Override
    public ResponseEntity<CommonResponse> registerVehicle(VehicleDTO vehicleDTO) {
        CommonResponse commonResponse = new CommonResponse();
        Vehicle vehicleExist = vehicleRepository.findByVehicleNumber(vehicleDTO.getVehicleNumber());
        response.clear();
        if (vehicleExist != null) {
            commonResponse.setErrorMessages(Collections.singletonList("Vehicle Number Already Exist."));
            commonResponse.setStatus(CommonConst.CONFLICT);
            return new ResponseEntity<>(commonResponse, HttpStatus.CONFLICT);
        } else {
            Vehicle vehicle = new ObjectMapper().convertValue(vehicleDTO, Vehicle.class);
            vehicle.setCreatedDate(new Date());
            vehicle.setCreatedBy("Nipuni");
            Vehicle vehicleSaved = vehicleRepository.save(vehicle);
            response.put("Vehicle", vehicleSaved);
            commonResponse.setPayload(Arrays.asList(response));
            commonResponse.setStatus(CommonConst.CREATED);
            return new ResponseEntity<>(commonResponse, HttpStatus.CREATED);
        }
    }

    @Override
    public List<VehicleDTO> getAllRegisteredVehicles() {
        return vehicleRepository
                .findAllDetails().stream()
                .map(vehicle -> new VehicleDTO(vehicle.getId(), vehicle.getVehicleNumber(), vehicle.getChassisNumber(), vehicle.getVehicleType(),vehicle.getFuelType()) {
                })
                .collect(Collectors.toList());
    }

    @Override
    public ResponseEntity<CommonResponse> getVehicleById(Long id) {
        return null;
    }

    @Override
    public ResponseEntity<CommonResponse> updateVehicle(Long id, VehicleDTO vehicleDTO) {
        return null;
    }

    @Override
    public ResponseEntity<CommonResponse> checkVehicleByVehicleNumber(String vehicleNumber) {
        return null;
    }
}
