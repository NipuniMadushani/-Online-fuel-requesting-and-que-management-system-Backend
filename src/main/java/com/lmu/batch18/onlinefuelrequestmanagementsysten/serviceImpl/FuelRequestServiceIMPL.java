package com.lmu.batch18.onlinefuelrequestmanagementsysten.serviceImpl;

import com.lmu.batch18.onlinefuelrequestmanagementsysten.dto.FuelRequestDTO;
import com.lmu.batch18.onlinefuelrequestmanagementsysten.models.Customer;
import com.lmu.batch18.onlinefuelrequestmanagementsysten.models.FuelRequest;
import com.lmu.batch18.onlinefuelrequestmanagementsysten.models.FuelStation;
import com.lmu.batch18.onlinefuelrequestmanagementsysten.models.User;
import com.lmu.batch18.onlinefuelrequestmanagementsysten.repository.CustomerRepository;
import com.lmu.batch18.onlinefuelrequestmanagementsysten.repository.FuelRequestRepository;
import com.lmu.batch18.onlinefuelrequestmanagementsysten.repository.FuelStationRepository;
import com.lmu.batch18.onlinefuelrequestmanagementsysten.service.FuelRequestService;
import com.lmu.batch18.onlinefuelrequestmanagementsysten.util.CommonConst;
import com.lmu.batch18.onlinefuelrequestmanagementsysten.util.CommonResponse;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class FuelRequestServiceIMPL implements FuelRequestService {

    @Autowired
    private FuelRequestRepository fuelRequestRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private FuelStationRepository fuelStationRepository;

    @Override
    public ResponseEntity<CommonResponse> saveFuelRequest(FuelRequestDTO fuelRequestDTO) {
        CommonResponse commonResponse = new CommonResponse();
        Customer customer = customerRepository.findById(fuelRequestDTO.getCustomerId()).get();
        FuelStation fuelStation = fuelStationRepository.findById(fuelRequestDTO.getFuelStationId()).get();
        if (customer == null) {
            commonResponse.setStatus(CommonConst.EXCEPTION_ERROR);
            commonResponse.setErrorMessages(Collections.singletonList("Not found customer"));
            return new ResponseEntity<>(commonResponse, HttpStatus.NOT_FOUND);
        }
        if (fuelStation == null) {
            commonResponse.setStatus(CommonConst.EXCEPTION_ERROR);
            commonResponse.setErrorMessages(Collections.singletonList("Not found fuel station"));
            return new ResponseEntity<>(commonResponse, HttpStatus.NOT_FOUND);
        }
        fuelRequestDTO.setCustomer(customer);
        fuelRequestDTO.setFuelStation(fuelStation);
        FuelRequest fuelRequest = modelMapper.map(fuelRequestDTO, FuelRequest.class);

        fuelRequestRepository.save(fuelRequest);


        commonResponse.setPayload(Collections.singletonList(fuelRequest));
        commonResponse.setStatus(1);
        return new ResponseEntity<>(commonResponse, HttpStatus.OK);
    }

    @Override
    public List<FuelRequestDTO> getFuelRequestsByCustomerId(int customerId) {
        Customer u = new Customer();
        u.setId(customerId);
        List<FuelRequest> fuelRequest = fuelRequestRepository.findByCustomerEquals(u);
        List<FuelRequestDTO> c = modelMapper.
                map(fuelRequest, new TypeToken<List<FuelRequestDTO>>() {
                }.getType());
        return c;


    }
}
