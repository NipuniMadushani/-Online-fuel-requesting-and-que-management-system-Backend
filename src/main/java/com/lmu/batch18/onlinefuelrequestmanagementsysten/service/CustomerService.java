package com.lmu.batch18.onlinefuelrequestmanagementsysten.service;

import com.lmu.batch18.onlinefuelrequestmanagementsysten.dto.CustomerDTO;
import com.lmu.batch18.onlinefuelrequestmanagementsysten.util.CommonResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface CustomerService {
    ResponseEntity<CommonResponse> saveCustomer(CustomerDTO customerDTO);

    CustomerDTO getCustomerByUserId(int userId);

    List<CustomerDTO> getALlCustomers();

    ResponseEntity<CommonResponse> updateCustomer(int id, CustomerDTO customerDTO);
}
