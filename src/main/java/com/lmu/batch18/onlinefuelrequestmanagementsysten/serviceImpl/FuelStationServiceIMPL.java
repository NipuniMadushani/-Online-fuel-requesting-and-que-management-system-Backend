package com.lmu.batch18.onlinefuelrequestmanagementsysten.serviceImpl;

import com.lmu.batch18.onlinefuelrequestmanagementsysten.dto.CustomerDTO;
import com.lmu.batch18.onlinefuelrequestmanagementsysten.dto.FuelStationDTO;
import com.lmu.batch18.onlinefuelrequestmanagementsysten.models.Customer;
import com.lmu.batch18.onlinefuelrequestmanagementsysten.models.FuelStation;
import com.lmu.batch18.onlinefuelrequestmanagementsysten.models.User;
import com.lmu.batch18.onlinefuelrequestmanagementsysten.repository.FuelStationRepository;
import com.lmu.batch18.onlinefuelrequestmanagementsysten.repository.UserRepository;
import com.lmu.batch18.onlinefuelrequestmanagementsysten.service.FuelStationService;
import com.lmu.batch18.onlinefuelrequestmanagementsysten.util.CommonConst;
import com.lmu.batch18.onlinefuelrequestmanagementsysten.util.CommonResponse;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class FuelStationServiceIMPL implements FuelStationService {
    @Autowired
    private FuelStationRepository fuelStationRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UserRepository userRepository;

    Map<String, Object> response = new HashMap<>();
    @Override
    public ResponseEntity<CommonResponse> saveFuelStation(FuelStationDTO fuelStationDTO) {
        CommonResponse commonResponse = new CommonResponse();
        User user = userRepository.getReferenceById(fuelStationDTO.getUserId());
        FuelStation fuelStation = fuelStationRepository.findByUserEquals(user);
        response.clear();
        if (fuelStation != null) {
            commonResponse.setErrorMessages(Collections.singletonList("User Id Already Exists."));
            commonResponse.setStatus(CommonConst.CONFLICT);
            return new ResponseEntity<>(commonResponse, HttpStatus.CONFLICT);
        } else {
            fuelStationDTO.setUser(user);
            FuelStation fuelStation1 = modelMapper.map(fuelStationDTO, FuelStation.class);
            //customer.setUser(user);
            FuelStation fuelStationSaved = fuelStationRepository.save(fuelStation1);
            response.put("Vehicle", fuelStationSaved);
            commonResponse.setPayload(Arrays.asList(response));
            commonResponse.setStatus(CommonConst.CREATED);
            return new ResponseEntity<>(commonResponse, HttpStatus.CREATED);
        }
    }

    @Override
    public FuelStationDTO getFuelStationByUserId(int userId) {
        User u = new User();
        u.setId(userId);
        FuelStation fuelStation = fuelStationRepository.findByUserEquals(u);
        FuelStationDTO c = modelMapper.map(fuelStation, FuelStationDTO.class);
        return c;
    }

    @Override
    public List<FuelStationDTO> getALlFuelStations() {
        List<FuelStation> fuelStations = fuelStationRepository.findAll();
        List<FuelStationDTO> c = modelMapper.
                map(fuelStations, new TypeToken<List<FuelStationDTO>>() {
                }.getType());
        return c;
    }

    @Override
    public ResponseEntity<CommonResponse> updateFuelStation(int id, FuelStationDTO fuelStationDTO) {
        CommonResponse commonResponse = new CommonResponse();
        FuelStation fuelStation = fuelStationRepository.findById(id).get();
        if (fuelStation==null) {
            commonResponse.setStatus(CommonConst.EXCEPTION_ERROR);
            commonResponse.setErrorMessages(Collections.singletonList("Not found "));
            return new ResponseEntity<>(commonResponse, HttpStatus.NOT_FOUND);
        }

        FuelStation fuelStation1 = fuelStationRepository.save(new FuelStation(
                fuelStation.getId(),
                fuelStationDTO.getName(),
                fuelStationDTO.getLocation(),
                fuelStation.isActiveState(),
                fuelStation.getUser(),
                null,
                null
        ));
        commonResponse.setPayload(Collections.singletonList(fuelStation1));
        commonResponse.setStatus(1);
        return new ResponseEntity<>(commonResponse, HttpStatus.OK);
    }

}
