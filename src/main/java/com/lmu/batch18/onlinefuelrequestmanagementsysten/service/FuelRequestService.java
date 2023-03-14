package com.lmu.batch18.onlinefuelrequestmanagementsysten.service;

import com.lmu.batch18.onlinefuelrequestmanagementsysten.dto.FuelRequestDTO;
import com.lmu.batch18.onlinefuelrequestmanagementsysten.models.Customer;
import com.lmu.batch18.onlinefuelrequestmanagementsysten.models.FuelRequest;
import com.lmu.batch18.onlinefuelrequestmanagementsysten.util.CommonResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface FuelRequestService {
    ResponseEntity<CommonResponse> saveFuelRequest(FuelRequest fuelRequestDTO);

    List<FuelRequestDTO> getFuelRequestsByCustomerId(int customerId);

    ResponseEntity<CommonResponse> getFuelRequestsByRequestId(int fuelRequestId);

    List<FuelRequestDTO> findAllFuelRequst();

    ResponseEntity<CommonResponse> getlastModifiedDate(int customerId);

    ResponseEntity<?> deleteFuelRequestsByRequestId(int fuelRequestId);

    ResponseEntity<?> approveFuelRequestsByRequestId(int fuelRequestId);

    ResponseEntity<?> rejectFuelRequestsByRequestId(int fuelRequestId);

    List allIncomeWeekly();

    List allTokenRequest();

    ResponseEntity<CommonResponse> updateFuelRequest(FuelRequest fuelRequestDTO);
}
