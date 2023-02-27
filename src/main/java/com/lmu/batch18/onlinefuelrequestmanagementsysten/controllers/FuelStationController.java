package com.lmu.batch18.onlinefuelrequestmanagementsysten.controllers;

import com.lmu.batch18.onlinefuelrequestmanagementsysten.dto.CustomerDTO;
import com.lmu.batch18.onlinefuelrequestmanagementsysten.dto.FuelStationDTO;
import com.lmu.batch18.onlinefuelrequestmanagementsysten.repository.RoleRepository;
import com.lmu.batch18.onlinefuelrequestmanagementsysten.service.FuelStationService;
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
@RequestMapping("/api/auth/v1/fuel-station")
@CrossOrigin("*")
@Slf4j
public class FuelStationController {
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private FuelStationService fuelStationService;

    @PostMapping("/")
    public ResponseEntity<CommonResponse> SaveFuelStation(@RequestBody FuelStationDTO fuelStationDTO) {
        CommonResponse commonResponse = new CommonResponse();
        try {
            return fuelStationService.saveFuelStation(fuelStationDTO);

        } catch (Exception ex) {
            ex.printStackTrace();
            commonResponse.setStatus(HttpStatus.EXPECTATION_FAILED.value());
            commonResponse.setErrorMessages(Collections.singletonList(ex.getMessage()));
            log.error("Error occurred while calling the save Fuel  Method : " + ex.getMessage());
            return new ResponseEntity<>(commonResponse, HttpStatus.EXPECTATION_FAILED);
        }
    }

    @GetMapping("station-by-user-id/{userId}")
    public ResponseEntity<CommonResponse> getAllRegisteredVehicles(@PathVariable("userId") int userId) {
        CommonResponse commonResponse = new CommonResponse();
        try {
            FuelStationDTO fuelStationDTO = fuelStationService.getFuelStationByUserId(userId);
            commonResponse.setPayload(Collections.singletonList(fuelStationDTO));
            return new ResponseEntity<>(commonResponse, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            commonResponse.setStatus(HttpStatus.EXPECTATION_FAILED.value());
            commonResponse.setErrorMessages(Collections.singletonList(e.getMessage()));
            log.error(e.getMessage());
            return new ResponseEntity<>(commonResponse, HttpStatus.EXPECTATION_FAILED);
        }
    }

//    @GetMapping("/manager/{userName}")
//    public ResponseEntity<CommonResponse> getFillingStationDetailsManagerWise(@PathVariable("userName") String  userName) {
//        CommonResponse commonResponse = new CommonResponse();
//        try {
//            responseEntity= fuelStationService.getFillingStationDetailsManagerWise(userName);
//            commonResponse.setPayload(Collections.singletonList(fuelStationDTO));
//            return new ResponseEntity<>(commonResponse, HttpStatus.OK);
//        } catch (Exception e) {
//            e.printStackTrace();
//            commonResponse.setStatus(HttpStatus.EXPECTATION_FAILED.value());
//            commonResponse.setErrorMessages(Collections.singletonList(e.getMessage()));
//            log.error(e.getMessage());
//            return new ResponseEntity<>(commonResponse, HttpStatus.EXPECTATION_FAILED);
//        }
//    }

    @GetMapping("/allFillingStations")
    public ResponseEntity<CommonResponse> allFuelStations() {
        CommonResponse commonResponse = new CommonResponse();
        try {
            List<FuelStationDTO> fuelStationDTOS = fuelStationService.getALlFuelStations();
            // commonResponse.setPayload(Collections.singletonList(fuelStationDTOS));
            commonResponse.setPayload(Collections.singletonList(fuelStationDTOS));
            return new ResponseEntity<>(commonResponse, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            commonResponse.setStatus(HttpStatus.EXPECTATION_FAILED.value());
            commonResponse.setErrorMessages(Collections.singletonList(e.getMessage()));
            log.error(e.getMessage());
            return new ResponseEntity<>(commonResponse, HttpStatus.EXPECTATION_FAILED);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<CommonResponse> updateFuelStation(@PathVariable("id") int id, @Valid @RequestBody FuelStationDTO fuelStationDTO) {
        CommonResponse commonResponse = new CommonResponse();
        try {
            return fuelStationService.updateFuelStation(id, fuelStationDTO);
        } catch (Exception e) {
            e.printStackTrace();
            commonResponse.setStatus(HttpStatus.EXPECTATION_FAILED.value());
            commonResponse.setErrorMessages(Collections.singletonList(e.getMessage()));
            log.error(e.getMessage());
            return new ResponseEntity<>(commonResponse, HttpStatus.EXPECTATION_FAILED);

        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<CommonResponse> getFillingStationsByCode(@PathVariable("id") int id) {
        ResponseEntity<CommonResponse> responseEntity = null;
        CommonResponse commonResponse = new CommonResponse();
        try {
            responseEntity = fuelStationService.getFuelStationById(id);
        } catch (Exception ex) {
            commonResponse.setStatus(HttpStatus.EXPECTATION_FAILED.value());
            commonResponse.setErrorMessages(Collections.singletonList(ex.getMessage()));
            log.error(ex.getMessage());
            return new ResponseEntity<>(commonResponse, HttpStatus.EXPECTATION_FAILED);
        }
        return responseEntity;
    }

    @GetMapping("/manager/{userName}")
    public ResponseEntity<CommonResponse> getFillingStationDetailsManagerWise(@PathVariable("userName") String userName) {
        ResponseEntity<CommonResponse> responseEntity = null;
        CommonResponse commonResponse = new CommonResponse();
        try {
            responseEntity = fuelStationService.getFillingStationDetailsManagerWise(userName);

        } catch (Exception e) {
            e.printStackTrace();
            commonResponse.setStatus(HttpStatus.EXPECTATION_FAILED.value());
            commonResponse.setErrorMessages(Collections.singletonList(e.getMessage()));
            log.error(e.getMessage());
            return new ResponseEntity<>(commonResponse, HttpStatus.EXPECTATION_FAILED);
        }
        return responseEntity;
    }

    @GetMapping("/allRegisteredFuelStationCount")
    public Integer getAllRegisteredFuelStationCount() {
        System.out.println(fuelStationService.getALlFuelStations());
        return fuelStationService.getAllRegisteredFuelStationCount();
    }
}
