package com.lmu.batch18.onlinefuelrequestmanagementsysten.controllers;

import com.lmu.batch18.onlinefuelrequestmanagementsysten.dto.FuelRequestByFuelStationDTO;
import com.lmu.batch18.onlinefuelrequestmanagementsysten.dto.FuelRequestDTO;
import com.lmu.batch18.onlinefuelrequestmanagementsysten.models.FuelRequest;
import com.lmu.batch18.onlinefuelrequestmanagementsysten.models.FuelRequestByFuelStation;
import com.lmu.batch18.onlinefuelrequestmanagementsysten.service.FuelRequestByFuelStationService;
import com.lmu.batch18.onlinefuelrequestmanagementsysten.util.CommonResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/auth/v1/fuel-request-by-fuel-station")
@CrossOrigin
@Slf4j
public class FuelRequestByFuelStationController {

    @Autowired
    private FuelRequestByFuelStationService fuelRequestByFuelStationService;

    @PostMapping("/")
    public ResponseEntity<CommonResponse> SaveFuelRequestByFuelStation(@RequestBody FuelRequestByFuelStation fuelRequestByFuelStationDTO) {
        CommonResponse commonResponse = new CommonResponse();
        try {
            return fuelRequestByFuelStationService.saveFuelRequestByFuelStation(fuelRequestByFuelStationDTO);

        } catch (Exception ex) {
            ex.printStackTrace();
            commonResponse.setStatus(HttpStatus.EXPECTATION_FAILED.value());
            commonResponse.setErrorMessages(Collections.singletonList(ex.getMessage()));
            log.error("Error occurred while calling the save fuel request  Method : " + ex.getMessage());
            return new ResponseEntity<>(commonResponse, HttpStatus.EXPECTATION_FAILED);
        }
    }

    @GetMapping("/details/{fuelRequestByFuelStationId}")
    public ResponseEntity<?> getFuelRequestByFuelStationById(@PathVariable(value = "fuelRequestByFuelStationId") int fuelRequestByFuelStationId) {
        CommonResponse commonResponse = new CommonResponse();
        ResponseEntity<?> responseEntity = null;
        try {
            responseEntity = fuelRequestByFuelStationService.getFuelRequestsById(fuelRequestByFuelStationId);
        } catch (Exception ex) {
            commonResponse.setStatus(HttpStatus.EXPECTATION_FAILED.value());
            commonResponse.setErrorMessages(Collections.singletonList(ex.getMessage()));
            log.error(ex.getMessage());
            return new ResponseEntity<>(commonResponse, HttpStatus.EXPECTATION_FAILED);
        }
        System.out.println(responseEntity);
        return responseEntity;
    }

    @GetMapping("/details-by-fuel-station/{fuelStationId}")
    public ResponseEntity<?> getFuelRequestByFuelStationByFuelStationId(@PathVariable(value = "fuelStationId") int fuelStationId) {
        CommonResponse commonResponse = new CommonResponse();
        ResponseEntity<?> responseEntity = null;
        try {
            responseEntity = fuelRequestByFuelStationService.getFuelRequestsByFuelStationId(fuelStationId);
        } catch (Exception ex) {
            commonResponse.setStatus(HttpStatus.EXPECTATION_FAILED.value());
            commonResponse.setErrorMessages(Collections.singletonList(ex.getMessage()));
            log.error(ex.getMessage());
            return new ResponseEntity<>(commonResponse, HttpStatus.EXPECTATION_FAILED);
        }
        System.out.println(responseEntity);
        return responseEntity;
    }


    @GetMapping("/all")
    public ResponseEntity<?> getAllNewFuelRequstByFuelStation() {
        ResponseEntity<?> responseEntity = null;
        CommonResponse commonResponse = new CommonResponse();
        try {
            List<FuelRequestByFuelStation> fuelRequestByFuelStations = fuelRequestByFuelStationService.findAllFuelRequstByFuelStation();
            commonResponse.setPayload(Collections.singletonList(fuelRequestByFuelStations));
            return new ResponseEntity<CommonResponse>(commonResponse, HttpStatus.OK);
        } catch (Exception ex) {
            commonResponse.setStatus(HttpStatus.EXPECTATION_FAILED.value());
            commonResponse.setErrorMessages(Collections.singletonList(ex.getMessage()));
            log.error(ex.getMessage());
            return new ResponseEntity<>(commonResponse, HttpStatus.EXPECTATION_FAILED);
        }
    }
    @PostMapping("/approveRequest/{fuelRequestId}")
    public ResponseEntity<?> approveFuelRequestByRequestId(@PathVariable("fuelRequestId") int fuelRequestId) {
        System.out.println(" appID:" + fuelRequestId);
        CommonResponse commonResponse = new CommonResponse();
        ResponseEntity<?> responseEntity = null;
        try {
            responseEntity = fuelRequestByFuelStationService.approveFuelRequestsByRequestId(fuelRequestId);
        } catch (Exception ex) {
            commonResponse.setStatus(HttpStatus.EXPECTATION_FAILED.value());
            commonResponse.setErrorMessages(Collections.singletonList(ex.getMessage()));
            log.error(ex.getMessage());
            return new ResponseEntity<>(commonResponse, HttpStatus.EXPECTATION_FAILED);
        }
        return responseEntity;
    }

    @PostMapping("/rejectRequest/{fuelRequestId}")
    public ResponseEntity<?> rejectFuelRequestByRequestId(@PathVariable("fuelRequestId") int fuelRequestId) {
        System.out.println(" appID:" + fuelRequestId);
        CommonResponse commonResponse = new CommonResponse();
        ResponseEntity<?> responseEntity = null;
        try {
            responseEntity = fuelRequestByFuelStationService.rejectFuelRequestsByRequestId(fuelRequestId);
        } catch (Exception ex) {
            commonResponse.setStatus(HttpStatus.EXPECTATION_FAILED.value());
            commonResponse.setErrorMessages(Collections.singletonList(ex.getMessage()));
            log.error(ex.getMessage());
            return new ResponseEntity<>(commonResponse, HttpStatus.EXPECTATION_FAILED);
        }
        return responseEntity;
    }

}
