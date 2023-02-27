package com.lmu.batch18.onlinefuelrequestmanagementsysten.controllers;

import com.lmu.batch18.onlinefuelrequestmanagementsysten.dto.CustomerDTO;
import com.lmu.batch18.onlinefuelrequestmanagementsysten.dto.VehicleDTO;
import com.lmu.batch18.onlinefuelrequestmanagementsysten.service.CustomerService;
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
@RequestMapping("/api/auth/v1/customer")
@CrossOrigin("*")
@Slf4j
public class CustomerController {
    @Autowired
    private CustomerService customerService;

    @PostMapping("/")
    public ResponseEntity<CommonResponse> SaveCustomer(@RequestBody CustomerDTO customerDTO) {
        CommonResponse commonResponse = new CommonResponse();
        try {
            return customerService.saveCustomer(customerDTO);

        } catch (Exception ex) {
            ex.printStackTrace();
            commonResponse.setStatus(HttpStatus.EXPECTATION_FAILED.value());
            commonResponse.setErrorMessages(Collections.singletonList(ex.getMessage()));
            log.error("Error occurred while calling the save Customer  Method : " + ex.getMessage());
            return new ResponseEntity<>(commonResponse, HttpStatus.EXPECTATION_FAILED);
        }
    }

    @GetMapping("Customer-by-user-id/{userId}")
    public ResponseEntity<CommonResponse> getAllRegisteredVehicles(@PathVariable("userId") int userId) {
        CommonResponse commonResponse = new CommonResponse();
        try {
            CustomerDTO customerDTOS = customerService.getCustomerByUserId(userId);
            commonResponse.setPayload(Collections.singletonList(customerDTOS));
            return new ResponseEntity<>(commonResponse, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            commonResponse.setStatus(HttpStatus.EXPECTATION_FAILED.value());
            commonResponse.setErrorMessages(Collections.singletonList(e.getMessage()));
            log.error(e.getMessage());
            return new ResponseEntity<>(commonResponse, HttpStatus.EXPECTATION_FAILED);
        }
    }

    @GetMapping("all-customer")
    public ResponseEntity<CommonResponse> allCustomers() {
        CommonResponse commonResponse = new CommonResponse();
        try {
            List<CustomerDTO> customerDTOS = customerService.getALlCustomers();
            commonResponse.setPayload(Collections.singletonList(customerDTOS));
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
    public ResponseEntity<CommonResponse> updateCustomer(@PathVariable("id") int id, @Valid @RequestBody CustomerDTO customerDTO) {
        CommonResponse commonResponse = new CommonResponse();
        try {
            return customerService.updateCustomer(id, customerDTO);
        } catch (Exception e) {
            e.printStackTrace();
            commonResponse.setStatus(HttpStatus.EXPECTATION_FAILED.value());
            commonResponse.setErrorMessages(Collections.singletonList(e.getMessage()));
            log.error( e.getMessage());
            return new ResponseEntity<>(commonResponse, HttpStatus.EXPECTATION_FAILED);

        }
    }

}
