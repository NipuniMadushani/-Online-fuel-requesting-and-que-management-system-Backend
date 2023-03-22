package com.lmu.batch18.onlinefuelrequestmanagementsysten.controllers;

import com.lmu.batch18.onlinefuelrequestmanagementsysten.dto.FuelRequestDTO;
import com.lmu.batch18.onlinefuelrequestmanagementsysten.dto.response.FuelRequestDailyIncomeDTO;
import com.lmu.batch18.onlinefuelrequestmanagementsysten.dto.response.FuelRequestFilterDTO;
import com.lmu.batch18.onlinefuelrequestmanagementsysten.models.EligibleQuota;
import com.lmu.batch18.onlinefuelrequestmanagementsysten.models.FuelPrice;
import com.lmu.batch18.onlinefuelrequestmanagementsysten.models.FuelRequest;
import com.lmu.batch18.onlinefuelrequestmanagementsysten.models.Vehicle;
import com.lmu.batch18.onlinefuelrequestmanagementsysten.repository.EligibleQuotaRepository;
import com.lmu.batch18.onlinefuelrequestmanagementsysten.repository.FuelPriceRepo;
import com.lmu.batch18.onlinefuelrequestmanagementsysten.repository.FuelRequestRepository;
import com.lmu.batch18.onlinefuelrequestmanagementsysten.repository.VehicleRepository;
import com.lmu.batch18.onlinefuelrequestmanagementsysten.service.FuelRequestService;
import com.lmu.batch18.onlinefuelrequestmanagementsysten.util.CommonResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/auth/v1/fuelrequest")
@CrossOrigin("*")
@Slf4j
public class FuelRequestController {
    @Autowired
    private FuelRequestService fuelRequestService;

    @Autowired
    private VehicleRepository vehicleRepository;

    @Autowired
    private EligibleQuotaRepository eligibleQuotaRepository;

    @Autowired
    private FuelRequestRepository fuelRequestRepository;

    @Autowired
    private FuelPriceRepo fuelPriceRepo;


    @PostMapping("/")
    public ResponseEntity<CommonResponse> SaveFuelRequest(@RequestBody FuelRequest fuelRequestDTO) {
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

    @GetMapping("/sunday")
    public LocalDateTime getSundayOfThisWeek() {
        LocalDateTime thisWeeksSunday = LocalDateTime.now().with(DayOfWeek.SUNDAY);
        return thisWeeksSunday;

    }


    @GetMapping("/unique/{customerId}")
    public ResponseEntity<CommonResponse> getFuelRequestByUser(@PathVariable("customerId") int customerId) {
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

    @GetMapping("/fuel-request-filter/{vehicleNumber}")
    public ResponseEntity<CommonResponse> fuelRequestFilter(@PathVariable("vehicleNumber") String vehicleNumber) {
        CommonResponse commonResponse = new CommonResponse();
        try {
            FuelRequestFilterDTO fuelRequestFilterDTO = new FuelRequestFilterDTO();
            Vehicle vehicle = vehicleRepository.findByVehicleNumber(vehicleNumber);
            List<FuelRequest> fuelRequest = fuelRequestRepository.findAllByVehicleEquals(vehicle);


            EligibleQuota eligibleQuota = eligibleQuotaRepository.findByVehicleTypeEquals(vehicle.getVehicleType());

            fuelRequestFilterDTO.setFuelType(vehicle.getFuelType());
            fuelRequestFilterDTO.setVehicleType(vehicle.getVehicleType());
            fuelRequestFilterDTO.setEligibleQuata(eligibleQuota.getQuota());

            fuelRequestFilterDTO.setVehicle(vehicle);

            if (fuelRequest.size() <= 0) {
                fuelRequestFilterDTO.setActualQuata(0.00);
                fuelRequestFilterDTO.setFuelAmount(0.00);
                System.out.println("there are no request from this vehicle in this id");
            } else {
                double count = 0;
                double fuelAmount = 0;
                for (FuelRequest f : fuelRequest) {
                    count = count + f.getActualQuata();
                    fuelAmount = fuelAmount + f.getFuelAmount();
                    fuelRequestFilterDTO.setConsumedState(f.isConsumedState());
                }

                fuelRequestFilterDTO.setActualQuata(count);
                fuelRequestFilterDTO.setFuelAmount(fuelAmount);

            }
            fuelRequestFilterDTO.setBalanceQuata(fuelRequestFilterDTO.getEligibleQuata() - fuelRequestFilterDTO.getActualQuata());
            fuelRequestFilterDTO.setFuelType(vehicle.getFuelType());
            fuelRequestFilterDTO.setVehicleType(vehicle.getVehicleType());

            FuelPrice fuelPrice = fuelPriceRepo.findByFuelTypeEquals(fuelRequestFilterDTO.getFuelType());
            fuelRequestFilterDTO.setPricePerLiter(fuelPrice.getPricePerLiter());


            commonResponse.setPayload(Collections.singletonList(fuelRequestFilterDTO));
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

    @GetMapping("/details/{fuelRequestId}")
    public ResponseEntity<?> getFuelRequestByRequstId(@PathVariable("fuelRequestId") int fuelRequestId) {
        System.out.println(fuelRequestId);
        CommonResponse commonResponse = new CommonResponse();
        ResponseEntity<?> responseEntity = null;
        try {
            responseEntity = fuelRequestService.getFuelRequestsByRequestId(fuelRequestId);
        } catch (Exception ex) {
            commonResponse.setStatus(HttpStatus.EXPECTATION_FAILED.value());
            commonResponse.setErrorMessages(Collections.singletonList(ex.getMessage()));
            log.error(ex.getMessage());
            return new ResponseEntity<>(commonResponse, HttpStatus.EXPECTATION_FAILED);
        }
        System.out.println(responseEntity);
        return responseEntity;
    }


    @GetMapping("/all")
    public ResponseEntity<?> getAllNewFuelRequst() {
        ResponseEntity<?> responseEntity = null;
        CommonResponse commonResponse = new CommonResponse();
        try {
            List<FuelRequestDTO> fuelRequestDTO = fuelRequestService.findAllFuelRequst();
            commonResponse.setPayload(Collections.singletonList(fuelRequestDTO));
            return new ResponseEntity<CommonResponse>(commonResponse, HttpStatus.OK);
        } catch (Exception ex) {
            commonResponse.setStatus(HttpStatus.EXPECTATION_FAILED.value());
            commonResponse.setErrorMessages(Collections.singletonList(ex.getMessage()));
            log.error(ex.getMessage());
            return new ResponseEntity<>(commonResponse, HttpStatus.EXPECTATION_FAILED);
        }
    }

    @GetMapping("/lastModified/{customerId}")
    public ResponseEntity<CommonResponse> findLasModified(@PathVariable("customerId") int customerId) {
        CommonResponse commonResponse = new CommonResponse();
        ResponseEntity<CommonResponse> responseEntity = null;
        try {
            responseEntity = fuelRequestService.getlastModifiedDate(customerId);
        } catch (Exception ex) {
            commonResponse.setStatus(HttpStatus.EXPECTATION_FAILED.value());
            commonResponse.setErrorMessages(Collections.singletonList(ex.getMessage()));
            log.error(ex.getMessage());
            return new ResponseEntity<>(commonResponse, HttpStatus.EXPECTATION_FAILED);
        }
        return responseEntity;
    }

    @DeleteMapping("/delete/{fuelRequestId}")
    public ResponseEntity<?> deleteFuelRequestByRequstId(@PathVariable("fuelRequestId") int fuelRequestId) {
        System.out.println("ID:" + fuelRequestId);
        CommonResponse commonResponse = new CommonResponse();
        ResponseEntity<?> responseEntity = null;
        try {
            responseEntity = fuelRequestService.deleteFuelRequestsByRequestId(fuelRequestId);
        } catch (Exception ex) {
            commonResponse.setStatus(HttpStatus.EXPECTATION_FAILED.value());
            commonResponse.setErrorMessages(Collections.singletonList(ex.getMessage()));
            log.error(ex.getMessage());
            return new ResponseEntity<>(commonResponse, HttpStatus.EXPECTATION_FAILED);
        }
        return responseEntity;
    }


    @GetMapping("/approveRequest/{fuelRequestId}")
    public ResponseEntity<?> approveFuelRequestByRequstId(@PathVariable("fuelRequestId") int fuelRequestId) {
        System.out.println(" appID:" + fuelRequestId);
        CommonResponse commonResponse = new CommonResponse();
        ResponseEntity<?> responseEntity = null;
        try {
            responseEntity = fuelRequestService.approveFuelRequestsByRequestId(fuelRequestId);
        } catch (Exception ex) {
            commonResponse.setStatus(HttpStatus.EXPECTATION_FAILED.value());
            commonResponse.setErrorMessages(Collections.singletonList(ex.getMessage()));
            log.error(ex.getMessage());
            return new ResponseEntity<>(commonResponse, HttpStatus.EXPECTATION_FAILED);
        }
        return responseEntity;
    }

    @GetMapping("/reject/{fuelRequestId}")
    public ResponseEntity<?> rejectFuelRequestByRequstId(@PathVariable("fuelRequestId") int fuelRequestId) {
        System.out.println(" rej ID:" + fuelRequestId);
        CommonResponse commonResponse = new CommonResponse();
        ResponseEntity<?> responseEntity = null;
        try {
            responseEntity = fuelRequestService.rejectFuelRequestsByRequestId(fuelRequestId);
        } catch (Exception ex) {
            commonResponse.setStatus(HttpStatus.EXPECTATION_FAILED.value());
            commonResponse.setErrorMessages(Collections.singletonList(ex.getMessage()));
            log.error(ex.getMessage());
            return new ResponseEntity<>(commonResponse, HttpStatus.EXPECTATION_FAILED);
        }
        return responseEntity;
    }

    @DeleteMapping(
            path = "delete-by-id/{id}"
    )
    public String deleteRequest(@PathVariable(value = "id") int requestId) {
        String deleted = fuelRequestService.deleteFuelRequest(requestId);
        return deleted;
    }

    @GetMapping("/allIncome")
    public List allIncomeWeekly() {
        CommonResponse commonResponse = new CommonResponse();
        ResponseEntity<?> responseEntity = null;

        return fuelRequestService.allIncomeWeekly();

    }

    @GetMapping("/allTokenRequest")
    public List totalTokenRequest() {
        return fuelRequestService.allTokenRequest();
    }


    @PutMapping("/")
    public ResponseEntity<CommonResponse> updateFuelRequest(@RequestBody FuelRequest fuelRequestDTO) {
        CommonResponse commonResponse = new CommonResponse();
        try {
            return fuelRequestService.updateFuelRequest(fuelRequestDTO);

        } catch (Exception ex) {
            ex.printStackTrace();
            commonResponse.setStatus(HttpStatus.EXPECTATION_FAILED.value());
            commonResponse.setErrorMessages(Collections.singletonList(ex.getMessage()));
            log.error("Error occurred while calling the save fuel request  Method : " + ex.getMessage());
            return new ResponseEntity<>(commonResponse, HttpStatus.EXPECTATION_FAILED);
        }
    }

    @GetMapping(
            path = "/get-daily-income-by-fuel-station-id",
            params = "fuelstationid"
    )
    public FuelRequestDailyIncomeDTO getIncomeByFuelStation(@RequestParam(value = "fuelstationid") int fuelstationid) {
        FuelRequestDailyIncomeDTO fuelRequestDailyIncome = fuelRequestService.getIncomeByFuelStationId(fuelstationid);
        return fuelRequestDailyIncome;
    }

}
