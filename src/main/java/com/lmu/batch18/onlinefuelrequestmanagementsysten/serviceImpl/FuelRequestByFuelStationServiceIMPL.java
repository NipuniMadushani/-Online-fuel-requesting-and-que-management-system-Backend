package com.lmu.batch18.onlinefuelrequestmanagementsysten.serviceImpl;

import com.lmu.batch18.onlinefuelrequestmanagementsysten.dto.FuelRequestByFuelStationDTO;
import com.lmu.batch18.onlinefuelrequestmanagementsysten.models.FuelRequest;
import com.lmu.batch18.onlinefuelrequestmanagementsysten.models.FuelRequestByFuelStation;
import com.lmu.batch18.onlinefuelrequestmanagementsysten.models.FuelStation;
import com.lmu.batch18.onlinefuelrequestmanagementsysten.repository.FuelRequestByFuelStationRepository;
import com.lmu.batch18.onlinefuelrequestmanagementsysten.repository.FuelStationRepository;
import com.lmu.batch18.onlinefuelrequestmanagementsysten.service.FuelRequestByFuelStationService;
import com.lmu.batch18.onlinefuelrequestmanagementsysten.util.CommonConst;
import com.lmu.batch18.onlinefuelrequestmanagementsysten.util.CommonResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Service
public class FuelRequestByFuelStationServiceIMPL implements FuelRequestByFuelStationService {
    @Autowired
    private FuelRequestByFuelStationRepository fuelRequestByFuelStationRepository;

    @Autowired
    private FuelStationRepository fuelStationRepository;
    @Override
    public ResponseEntity<CommonResponse> saveFuelRequestByFuelStation(FuelRequestByFuelStation fuelRequestByFuelStationDTO) {
        CommonResponse commonResponse = new CommonResponse();
        fuelRequestByFuelStationDTO.setCreatedDate(new Date());
        fuelRequestByFuelStationDTO.setUpdatedDate(new Date());
        FuelRequestByFuelStation fuelRequestByFuelStation = fuelRequestByFuelStationRepository.save(fuelRequestByFuelStationDTO);


        commonResponse.setPayload(Collections.singletonList(fuelRequestByFuelStation));
        commonResponse.setStatus(1);
        return new ResponseEntity<>(commonResponse, HttpStatus.OK);

    }

    @Override
    public ResponseEntity<CommonResponse> getFuelRequestsById(int fuelRequestId) {
        CommonResponse commonResponse = new CommonResponse();
        FuelRequestByFuelStation fuelRequest = fuelRequestByFuelStationRepository.findById(fuelRequestId).get();

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
    public ResponseEntity<CommonResponse> getFuelRequestsByFuelStationId(int fuelStationId) {
        CommonResponse commonResponse = new CommonResponse();
        FuelStation fuelStation = fuelStationRepository.findById(fuelStationId).get();

        if (fuelStation == null) {
            commonResponse.setErrorMessages(Arrays.asList("Not Found Fuel Request Under This ID"));
            commonResponse.setStatus(CommonConst.NOT_FOUND_RECORD);
            return new ResponseEntity<>(commonResponse, HttpStatus.NOT_FOUND);
        } else {
            List<FuelRequestByFuelStation> list = fuelRequestByFuelStationRepository.findAllByFuelStation(fuelStation);

            commonResponse.setStatus(1);
            commonResponse.setPayload(Collections.singletonList(list));
            return new ResponseEntity<>(commonResponse, HttpStatus.OK);
        }
    }

    @Override
    public List<FuelRequestByFuelStation> findAllFuelRequstByFuelStation() {
        return fuelRequestByFuelStationRepository.findAll();
    }
}
