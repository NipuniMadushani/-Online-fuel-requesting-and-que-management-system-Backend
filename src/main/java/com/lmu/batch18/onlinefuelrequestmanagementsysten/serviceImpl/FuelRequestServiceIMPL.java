package com.lmu.batch18.onlinefuelrequestmanagementsysten.serviceImpl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lmu.batch18.onlinefuelrequestmanagementsysten.dto.FuelRequestDTO;
import com.lmu.batch18.onlinefuelrequestmanagementsysten.dto.VehicleDTO;
import com.lmu.batch18.onlinefuelrequestmanagementsysten.models.*;
import com.lmu.batch18.onlinefuelrequestmanagementsysten.repository.*;
import com.lmu.batch18.onlinefuelrequestmanagementsysten.service.FuelRequestService;
import com.lmu.batch18.onlinefuelrequestmanagementsysten.util.CommonConst;
import com.lmu.batch18.onlinefuelrequestmanagementsysten.util.CommonResponse;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

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

    @Autowired
    private VehicleRepository vehicleRepository;
    Map<String, Object> response = new HashMap<>();
    @Autowired
    private FuelPriceRepo fuelPriceRepo;

    @Override
    public ResponseEntity<CommonResponse> saveFuelRequest(FuelRequest fuelRequestDTO) {
        CommonResponse commonResponse = new CommonResponse();
//        Customer customer = customerRepository.findById(fuelRequestDTO.getCustomerId()).get();
//        FuelStation fuelStation = fuelStationRepository.findById(fuelRequestDTO.getFuelStationId()).get();
//        Vehicle vehicle = vehicleRepository.findById(fuelRequestDTO.getVehicleId()).get();
//        if (customer == null) {
//            commonResponse.setStatus(CommonConst.EXCEPTION_ERROR);
//            commonResponse.setErrorMessages(Collections.singletonList("Not found customer"));
//            return new ResponseEntity<>(commonResponse, HttpStatus.NOT_FOUND);
//        }
//        if (fuelStation == null) {
//            commonResponse.setStatus(CommonConst.EXCEPTION_ERROR);
//            commonResponse.setErrorMessages(Collections.singletonList("Not found fuel station"));
//            return new ResponseEntity<>(commonResponse, HttpStatus.NOT_FOUND);
//        }
//        if(vehicle == null){
//            commonResponse.setStatus(CommonConst.EXCEPTION_ERROR);
//            commonResponse.setErrorMessages(Collections.singletonList("Not found vehicle"));
//            return new ResponseEntity<>(commonResponse, HttpStatus.NOT_FOUND);
//        }
//        fuelRequestDTO.setCustomer(customer);
//        fuelRequestDTO.setFuelStation(fuelStation);
//        fuelRequestDTO.setVehicle(vehicle);
//        FuelRequest fuelRequest = modelMapper.map(fuelRequestDTO, FuelRequest.class);
        fuelRequestDTO.setCreatedDate(new Date());
        fuelRequestDTO.setUpdatedDate(new Date());
        FuelRequest fuelRequest = fuelRequestRepository.save(fuelRequestDTO);


        commonResponse.setPayload(Collections.singletonList(fuelRequest));
        commonResponse.setStatus(1);
        return new ResponseEntity<>(commonResponse, HttpStatus.OK);
    }

    @Override
    public List<FuelRequestDTO> getFuelRequestsByCustomerId(int userId) {
        return fuelRequestRepository.findByUserId(userId).stream()
                .map(fuelRequest -> new FuelRequestDTO(fuelRequest.getId(), fuelRequest.getRequestedDate(), fuelRequest.getVehicleType(), fuelRequest.getEligibleQuata(), fuelRequest.getActualQuata(), fuelRequest.isApproval_state()) {
                })
                .collect(Collectors.toList());
    }

    @Override
    public ResponseEntity<CommonResponse> getFuelRequestsByRequestId(int fuelRequestId) {
        CommonResponse commonResponse = new CommonResponse();
        FuelRequest fuelRequest = fuelRequestRepository.findById(fuelRequestId).get();

        if (fuelRequest == null) {
            commonResponse.setErrorMessages(Arrays.asList("Not Found Fuel Request Under This ID"));
            commonResponse.setStatus(CommonConst.NOT_FOUND_RECORD);
            return new ResponseEntity<>(commonResponse, HttpStatus.NOT_FOUND);
        } else {

            commonResponse.setStatus(1);
            commonResponse.setPayload(Collections.singletonList(fuelRequest));
            return new ResponseEntity<>(commonResponse, HttpStatus.OK);
        }

    }

    @Override
    public List<FuelRequestDTO> findAllFuelRequst() {
        return fuelRequestRepository.findAll().stream()
                .map(fuelRequest -> new FuelRequestDTO(fuelRequest.getId(), fuelRequest.getRequestedDate(), fuelRequest.getVehicleType(), fuelRequest.getEligibleQuata(), fuelRequest.getActualQuata(), fuelRequest.isApproval_state(),fuelRequest.getScheduleTime(),fuelRequest.getFuelStation()) {
                })
                .collect(Collectors.toList());
    }

    @Override
    public ResponseEntity<CommonResponse> getlastModifiedDate(int customerId) {
        CommonResponse commonResponse = new CommonResponse();
        Map<String, String> map = new HashMap<>();
        String datetime = null;
        try {
            List<FuelRequest> hotelMains = fuelRequestRepository.findByUserId(customerId);
            System.out.println(hotelMains);
            if (hotelMains.isEmpty()) {
                commonResponse.setStatus(-1);
                commonResponse.setErrorMessages(Collections.singletonList("Not found records"));
            } else {
                FuelRequest hotelMain = Collections.max(hotelMains, Comparator.comparing(FuelRequest::getUpdatedDate));
                datetime = hotelMain.getUpdatedDate().toString();
            }

            map.put("dateTime", datetime.replace("T", " "));

            commonResponse.setPayload(Collections.singletonList(map));
            commonResponse.setStatus(1);
        } catch (Exception ex) {
            commonResponse.setStatus(CommonConst.EXCEPTION_ERROR);
            return new ResponseEntity<>(commonResponse, HttpStatus.EXPECTATION_FAILED);
        }
        return new ResponseEntity<>(commonResponse, HttpStatus.OK);
    }

    @Override
    public String deleteFuelRequest(int requestId) {
        if(fuelRequestRepository.existsById(requestId)){
            fuelRequestRepository.deleteById(requestId);
            return "deleted successfully "+ requestId;
        }
        throw new RuntimeException("no data found for that id");
    }
}
