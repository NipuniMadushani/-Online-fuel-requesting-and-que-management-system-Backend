package com.lmu.batch18.onlinefuelrequestmanagementsysten.controllers;

import com.lmu.batch18.onlinefuelrequestmanagementsysten.dto.CustomerDTO;
import com.lmu.batch18.onlinefuelrequestmanagementsysten.dto.FuelRequestDTO;
import com.lmu.batch18.onlinefuelrequestmanagementsysten.service.CustomerService;
import com.lmu.batch18.onlinefuelrequestmanagementsysten.service.FuelRequestService;
import com.lmu.batch18.onlinefuelrequestmanagementsysten.util.CommonResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/auth/v1/fuelrequest")
@CrossOrigin("*")
@Slf4j
public class FuelRequestController {
    @Autowired
    private FuelRequestService fuelRequestService;

    @PostMapping("/")
    public ResponseEntity<CommonResponse> SaveFuelRequest(@RequestBody FuelRequestDTO fuelRequestDTO) {
        CommonResponse commonResponse = new CommonResponse();
        try {
            return fuelRequestService.saveFuelRequest(fuelRequestDTO);

        } catch (Exception ex) {
            ex.printStackTrace();
            commonResponse.setStatus(HttpStatus.EXPECTATION_FAILED.value());
            commonResponse.setErrorMessages(Collections.singletonList(ex.getMessage()));
            log.error("Error occurred while calling the save fuel request  Method : " + ex.getMessage());
            return new ResponseEntity<>(commonResponse, HttpStatus.EXPECTATION_FAILED);
        }
    }

    @GetMapping("request-by-customer-id/{customerId}")
    public ResponseEntity<CommonResponse> getAllRegisteredVehicles(@PathVariable("customerId") int customerId) {
        CommonResponse commonResponse = new CommonResponse();
        try {

            List<FuelRequestDTO> fuelRequestDTO = fuelRequestService.getFuelRequestsByCustomerId(customerId);
           commonResponse.setPayload(Collections.singletonList(fuelRequestDTO));
            //commonResponse.setPayload(Arrays.asList(fuelRequestDTO.toArray()));

            return new ResponseEntity<CommonResponse>(commonResponse, HttpStatus.OK);

        } catch (Exception e) {
            e.printStackTrace();
            commonResponse.setStatus(HttpStatus.EXPECTATION_FAILED.value());
            commonResponse.setErrorMessages(Collections.singletonList(e.getMessage()));
            log.error(e.getMessage());
            return new ResponseEntity<>(commonResponse, HttpStatus.EXPECTATION_FAILED);
        }
    }
}
