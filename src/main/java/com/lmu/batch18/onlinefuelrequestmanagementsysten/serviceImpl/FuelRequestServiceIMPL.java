package com.lmu.batch18.onlinefuelrequestmanagementsysten.serviceImpl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lmu.batch18.onlinefuelrequestmanagementsysten.dto.FuelRequestDTO;
import com.lmu.batch18.onlinefuelrequestmanagementsysten.dto.VehicleDTO;
import com.lmu.batch18.onlinefuelrequestmanagementsysten.dto.WeeklyIncomeDTO;
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

import java.text.DateFormat;
import java.text.SimpleDateFormat;
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


    DateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");
    @Override
    public ResponseEntity<CommonResponse> saveFuelRequest(FuelRequest fuelRequestDTO) {
        CommonResponse commonResponse = new CommonResponse();
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
                .map(fuelRequest -> new FuelRequestDTO(fuelRequest.getId(), fuelRequest.getRequestedDate(),
                        fuelRequest.getVehicleType(), fuelRequest.getEligibleQuata(), fuelRequest.getActualQuata(),
                        fuelRequest.isApprovalState(),fuelRequest.getScheduleTime(),fuelRequest.getFuelStation(),
                        fuelRequest.getFuelAmount(),fuelRequest.isRejectState()) {
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
                .map(fuelRequest -> new FuelRequestDTO(fuelRequest.getId(), fuelRequest.getRequestedDate(),
                        fuelRequest.getVehicleType(), fuelRequest.getEligibleQuata(), fuelRequest.getActualQuata(),
                        fuelRequest.isApprovalState(),fuelRequest.getScheduleTime(),fuelRequest.getFuelStation(),
                        fuelRequest.getFuelAmount(),fuelRequest.isRejectState()) {
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
    public ResponseEntity<?> deleteFuelRequestsByRequestId(int fuelRequestId) {
        CommonResponse commonResponse = new CommonResponse();
        FuelRequest fuelRequest = fuelRequestRepository.findById(fuelRequestId).get();

        if (fuelRequest == null) {

//            commonResponse.setErrorMessages(Arrays.asList("Not Found Fuel Request Under This ID"));
            commonResponse.setStatus(CommonConst.NOT_FOUND_RECORD);
            return new ResponseEntity<>(commonResponse, HttpStatus.NOT_FOUND);
        } else {
            fuelRequestRepository.deleteById(fuelRequestId);
            commonResponse.setStatus(1);
            commonResponse.setErrorMessages(Arrays.asList("SuccessFully Deleted"));
            return new ResponseEntity<>(commonResponse, HttpStatus.OK);
        }
    }

    @Override
    public ResponseEntity<?> approveFuelRequestsByRequestId(int fuelRequestId) {
        System.out.println("appr ser id:"+fuelRequestId);
        CommonResponse commonResponse = new CommonResponse();
        FuelRequest fuelRequest = fuelRequestRepository.findById(fuelRequestId).get();

        if (fuelRequest == null) {

//            commonResponse.setErrorMessages(Arrays.asList("Not Found Fuel Request Under This ID"));
            commonResponse.setStatus(CommonConst.NOT_FOUND_RECORD);
            return new ResponseEntity<>(commonResponse, HttpStatus.NOT_FOUND);
        } else {
            fuelRequestRepository.updateApproveById(fuelRequestId);
            commonResponse.setStatus(1);
            commonResponse.setErrorMessages(Arrays.asList("SuccessFully Rejected"));
            return new ResponseEntity<>(commonResponse, HttpStatus.OK);
        }
    }

    @Override
    public ResponseEntity<?> rejectFuelRequestsByRequestId(int fuelRequestId) {
        CommonResponse commonResponse = new CommonResponse();
        FuelRequest fuelRequest = fuelRequestRepository.findById(fuelRequestId).get();

        if (fuelRequest == null) {

//            commonResponse.setErrorMessages(Arrays.asList("Not Found Fuel Request Under This ID"));
            commonResponse.setStatus(CommonConst.NOT_FOUND_RECORD);
            return new ResponseEntity<>(commonResponse, HttpStatus.NOT_FOUND);
        } else {
            fuelRequestRepository.updateRejectStatusById(fuelRequestId);
            commonResponse.setStatus(1);
            commonResponse.setErrorMessages(Arrays.asList("SuccessFully Rejected"));
            return new ResponseEntity<>(commonResponse, HttpStatus.OK);
        }
    }

    @Override
    public List allIncomeWeekly() {
            List<Object[]> weeklyIncomeDTOS=  fuelRequestRepository.getAllWeeklyIncome();
            return weeklyIncomeDTOS;
    }

    @Override
    public List allTokenRequest() {
        List<Object[]> allTokenRequest=  fuelRequestRepository.allTokenRequest();return allTokenRequest;
    }

    @Override
    public ResponseEntity<CommonResponse> updateFuelRequest(FuelRequest fuelRequestDTO) {
        CommonResponse commonResponse = new CommonResponse();

        FuelRequest fuelRequestExist=fuelRequestRepository.findById(fuelRequestDTO.getId()).get();
        if(fuelRequestExist==null){
            commonResponse.setErrorMessages(Arrays.asList("Not Found Fuel Request Under This ID"));
            commonResponse.setStatus(CommonConst.NOT_FOUND_RECORD);
            return new ResponseEntity<>(commonResponse, HttpStatus.NOT_FOUND);
        }else{
            fuelRequestDTO.setCreatedDate(fuelRequestExist.getCreatedDate());
            fuelRequestDTO.setUpdatedDate(new Date());
            fuelRequestDTO.setId(fuelRequestDTO.getId());
            fuelRequestRepository.updateConsumeStateForFuelRequest(fuelRequestDTO.getId());
//            FuelRequest fuelRequest = fuelRequestRepository.save(fuelRequestDTO);
//            commonResponse.setPayload(Collections.singletonList(fuelRequest));
            commonResponse.setStatus(1);
            return new ResponseEntity<>(commonResponse, HttpStatus.OK);
        }

    }
}
