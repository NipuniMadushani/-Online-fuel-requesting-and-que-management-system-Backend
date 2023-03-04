package com.lmu.batch18.onlinefuelrequestmanagementsysten.controllers;

import com.lmu.batch18.onlinefuelrequestmanagementsysten.dto.VehicleDTO;
import com.lmu.batch18.onlinefuelrequestmanagementsysten.service.VehicleService;
import com.lmu.batch18.onlinefuelrequestmanagementsysten.util.CommonResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/auth/v1/Vehicle")
@CrossOrigin("*")
@Slf4j
public class VehicleController {
    @Autowired
    private VehicleService vehicleService;


    @PostMapping("/")
    public ResponseEntity<CommonResponse> registerVehicle(@RequestBody VehicleDTO vehicleDTO) {
        CommonResponse commonResponse = new CommonResponse();
        try {
            return vehicleService.registerVehicle(vehicleDTO);
        } catch (Exception ex) {
            ex.printStackTrace();
            commonResponse.setStatus(HttpStatus.EXPECTATION_FAILED.value());
            commonResponse.setErrorMessages(Collections.singletonList(ex.getMessage()));
            log.error("Error occurred while calling the save Vehicle  Method : " + ex.getMessage());
            return new ResponseEntity<>(commonResponse, HttpStatus.EXPECTATION_FAILED);
        }
    }

    @GetMapping("/allRegistered/{userId}")
    public ResponseEntity<CommonResponse> getAllRegisteredVehicles(@PathVariable("userId") String userId) {
        System.out.println(userId);
        CommonResponse commonResponse = new CommonResponse();
        try {
            List<VehicleDTO> vehicleDTOS = vehicleService.getAllRegisteredVehicles(userId);
            commonResponse.setPayload(Collections.singletonList(vehicleDTOS));
            return new ResponseEntity<>(commonResponse, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            commonResponse.setStatus(HttpStatus.EXPECTATION_FAILED.value());
            commonResponse.setErrorMessages(Collections.singletonList(e.getMessage()));
            log.error(e.getMessage());
            return new ResponseEntity<>(commonResponse, HttpStatus.EXPECTATION_FAILED);
        }
    }

    @GetMapping("/vehicleNumberDuplicate/{vehicleNumber}")
    public ResponseEntity<CommonResponse> checkVehicleByVehicleNumber(@PathVariable("vehicleNumber") String vehicleNumber) {
        CommonResponse commonResponse = new CommonResponse();
        ResponseEntity<CommonResponse> responseEntity = null;
        try {
            responseEntity = vehicleService.checkVehicleByVehicleNumber(vehicleNumber);
        } catch (Exception ex) {
            commonResponse.setStatus(HttpStatus.EXPECTATION_FAILED.value());
            commonResponse.setErrorMessages(Collections.singletonList(ex.getMessage()));
            log.error(ex.getMessage());
            return new ResponseEntity<>(commonResponse, HttpStatus.EXPECTATION_FAILED);
        }
        return responseEntity;
    }

    @GetMapping("/chassisNumberDuplicate/{chassisNumber}")
    public ResponseEntity<CommonResponse> checkVehicleByChassisNumber(@PathVariable("chassisNumber") String chassisNumber) {
        CommonResponse commonResponse = new CommonResponse();
        ResponseEntity<CommonResponse> responseEntity = null;
        try {
            responseEntity = vehicleService.checkVehicleByChassisNumber(chassisNumber);
        } catch (Exception ex) {
            commonResponse.setStatus(HttpStatus.EXPECTATION_FAILED.value());
            commonResponse.setErrorMessages(Collections.singletonList(ex.getMessage()));
            log.error(ex.getMessage());
            return new ResponseEntity<>(commonResponse, HttpStatus.EXPECTATION_FAILED);
        }
        return responseEntity;
    }

    @GetMapping("/{id}")
    public ResponseEntity<CommonResponse> getVehicleById(@PathVariable("id") int id) {

        ResponseEntity<CommonResponse> responseEntity = null;
        CommonResponse commonResponse = new CommonResponse();
        try {
            responseEntity = vehicleService.getVehicleById(id);
        } catch (Exception ex) {
            commonResponse.setStatus(HttpStatus.EXPECTATION_FAILED.value());
            commonResponse.setErrorMessages(Collections.singletonList(ex.getMessage()));
            log.error(ex.getMessage());
            return new ResponseEntity<>(commonResponse, HttpStatus.EXPECTATION_FAILED);
        }
        return responseEntity;
    }

    @PutMapping("/{id}")
    public ResponseEntity<CommonResponse> updateVehicle(@PathVariable("id") int id, @Valid @RequestBody VehicleDTO vehicleDTO) {
        CommonResponse commonResponse = new CommonResponse();
        try {
            return vehicleService.updateVehicle(id, vehicleDTO);
        } catch (Exception e) {
            e.printStackTrace();
            commonResponse.setStatus(HttpStatus.EXPECTATION_FAILED.value());
            commonResponse.setErrorMessages(Collections.singletonList(e.getMessage()));
            log.error(e.getMessage());
            return new ResponseEntity<>(commonResponse, HttpStatus.EXPECTATION_FAILED);

        }
    }

    @GetMapping("/allRegisteredVehiclesCount")
    public Integer getAllRegisteredVehiclesCount() {
        return vehicleService.allRegisteredVehiclesCount();
    }
}
