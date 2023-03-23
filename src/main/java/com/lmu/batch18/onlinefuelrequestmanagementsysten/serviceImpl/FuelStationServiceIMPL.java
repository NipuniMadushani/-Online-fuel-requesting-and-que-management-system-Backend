package com.lmu.batch18.onlinefuelrequestmanagementsysten.serviceImpl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lmu.batch18.onlinefuelrequestmanagementsysten.controllers.AuthController;
import com.lmu.batch18.onlinefuelrequestmanagementsysten.dto.FuelConsumeDTO;
import com.lmu.batch18.onlinefuelrequestmanagementsysten.dto.FuelRequestDTO;
import com.lmu.batch18.onlinefuelrequestmanagementsysten.dto.FuelStationDTO;
import com.lmu.batch18.onlinefuelrequestmanagementsysten.models.FuelStation;
import com.lmu.batch18.onlinefuelrequestmanagementsysten.payload.request.SignupRequest;
import com.lmu.batch18.onlinefuelrequestmanagementsysten.repository.FuelStationRepository;
import com.lmu.batch18.onlinefuelrequestmanagementsysten.repository.UserRepository;
import com.lmu.batch18.onlinefuelrequestmanagementsysten.security.services.UserDetailsServiceImpl;
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

    @Autowired
    UserDetailsServiceImpl userDetailsService;

    @Autowired
    AuthController authController;

    Map<String, Object> response = new HashMap<>();

    @Override
    public ResponseEntity<CommonResponse> saveFuelStation(FuelStationDTO fuelStationDTO) {
        CommonResponse commonResponse = new CommonResponse();
        FuelStation fuelStation = fuelStationRepository.findByDisplayName(fuelStationDTO.getDisplayName());
//        FuelStation fuelStation = fuelStationRepository.findByUserEquals(user);
        response.clear();
        if (fuelStation != null) {
            commonResponse.setErrorMessages(Collections.singletonList("Fuel Station Name Already Exists."));
            commonResponse.setStatus(CommonConst.CONFLICT);
            return new ResponseEntity<>(commonResponse, HttpStatus.CONFLICT);
        } else {
            fuelStationDTO.setCreatedDate(new Date());
//            fuelStationDTO.setUser(user);
            FuelStation fuelStation1 = modelMapper.map(fuelStationDTO, FuelStation.class);
            //customer.setUser(user);
            FuelStation fuelStationSaved = fuelStationRepository.save(fuelStation1);
            response.put("FuelStation", fuelStationSaved);
            if (fuelStationSaved != null) {
                String randomPassword = userDetailsService.sendEmailOTP(fuelStationSaved);
                System.out.println(randomPassword);
                SignupRequest signupRequest = new SignupRequest();
                signupRequest.setUsername(fuelStation1.getManagerFirstName());
                signupRequest.setEmail(fuelStation1.getManagerEmail());
                signupRequest.setRole(Collections.singleton("fuelstationadmin"));
                signupRequest.setPassword(randomPassword);
//    userRepository.save(signupRequest);
                authController.registerUser(signupRequest);
            }
            commonResponse.setPayload(Arrays.asList(response));
            commonResponse.setStatus(CommonConst.CREATED);
            return new ResponseEntity<>(commonResponse, HttpStatus.CREATED);
        }
    }

    @Override
    public FuelStationDTO getFuelStationByUserId(int userId) {
//        User u = new User();
//        u.setId(userId);
//        FuelStation fuelStation = fuelStationRepository.findByUserEquals(u);
//        FuelStationDTO c = modelMapper.map(fuelStation, FuelStationDTO.class);
        return null;
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
        if (fuelStation == null) {
            commonResponse.setStatus(CommonConst.EXCEPTION_ERROR);
            commonResponse.setErrorMessages(Collections.singletonList("Not found Fuel Station "));
            return new ResponseEntity<>(commonResponse, HttpStatus.NOT_FOUND);
        }

        fuelStationDTO.setCreatedDate(new Date());
//            fuelStationDTO.setUser(user);
        FuelStation fuelStation1 = modelMapper.map(fuelStationDTO, FuelStation.class);
        fuelStation1.setId(fuelStationDTO.getId());
        FuelStation fuelStationSaved = fuelStationRepository.save(fuelStation1);
        response.put("FuelStation", fuelStationSaved);

        commonResponse.setPayload(Collections.singletonList(fuelStationSaved));
        commonResponse.setStatus(1);
        return new ResponseEntity<>(commonResponse, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<CommonResponse> getFuelStationById(int id) {
        CommonResponse commonResponse = new CommonResponse();
        FuelStation fuelStation = fuelStationRepository.findById(id).get();
        if (fuelStation == null) {
            commonResponse.setStatus(CommonConst.EXCEPTION_ERROR);
            commonResponse.setErrorMessages(Collections.singletonList("Not found Vehicle"));
            return new ResponseEntity<>(commonResponse, HttpStatus.NOT_FOUND);
        }

        FuelStationDTO fuelStationDTO = new ObjectMapper().convertValue(fuelStation, FuelStationDTO.class);
        System.out.println("fuelStation:" + fuelStationDTO);
        commonResponse.setStatus(1);
        commonResponse.setPayload(Collections.singletonList(fuelStationDTO));
        return new ResponseEntity<>(commonResponse, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<CommonResponse> getFillingStationDetailsManagerWise(String userName) {
        CommonResponse commonResponse = new CommonResponse();
        FuelStation fuelStation = fuelStationRepository.findByManagerFirstName(userName);
        if (fuelStation == null) {
            commonResponse.setStatus(CommonConst.EXCEPTION_ERROR);
            commonResponse.setErrorMessages(Collections.singletonList("Not found Vehicle"));
            return new ResponseEntity<>(commonResponse, HttpStatus.NOT_FOUND);
        }

        FuelStationDTO fuelStationDTO = new ObjectMapper().convertValue(fuelStation, FuelStationDTO.class);
        System.out.println("fuelStation:" + fuelStationDTO);
        commonResponse.setStatus(1);
        commonResponse.setPayload(Collections.singletonList(fuelStationDTO));
        return new ResponseEntity<>(commonResponse, HttpStatus.OK);
    }

    @Override
    public Integer getAllRegisteredFuelStationCount() {
        return fuelStationRepository.getAllRegisteredFuelStationCount();
    }

    @Override
    public FuelConsumeDTO  getRemainingFuelCount(int id, String type) {
        double consumedCount=fuelStationRepository.getRemainingFuelCount(id,type);
      double fullPetrolStock= fuelStationRepository.getPetrolOrDiselStock(id,type);
      double remainingStock=fullPetrolStock-consumedCount;

        FuelConsumeDTO fuelConsumeDTO = new FuelConsumeDTO();
//        if(type=="petrol"){
        fuelConsumeDTO.setConsumePetrol(fuelStationRepository.getRemainingFuelCount(id, type));
        fuelConsumeDTO.setFullPetrolStock(fullPetrolStock);
        fuelConsumeDTO.setRemainingPetrol(remainingStock);
        if (type.equals("petrol")) {
            fuelConsumeDTO.setPricePerLiter(300);
        } else {
            fuelConsumeDTO.setPricePerLiter(225);
        }

        return fuelConsumeDTO;

    }

}
