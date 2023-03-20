package com.lmu.batch18.onlinefuelrequestmanagementsysten.controllers;

import com.lmu.batch18.onlinefuelrequestmanagementsysten.models.NewSchedule;
import com.lmu.batch18.onlinefuelrequestmanagementsysten.service.NewScheduleService;
import com.lmu.batch18.onlinefuelrequestmanagementsysten.util.CommonResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/api/auth/v1/new-schedule")
@CrossOrigin("*")
@Slf4j

public class NewScheduleController {
    @Autowired
    private NewScheduleService newScheduleService;

    @PostMapping("/")
    public NewSchedule saveAndSendEmailForNewSchedule(@RequestBody NewSchedule newSchedule) {
        CommonResponse commonResponse = new CommonResponse();
        try {
            System.out.println(newSchedule.getUserId());
            return newScheduleService.saveAndSendEmailForNewSchedule(newSchedule);
        } catch (Exception ex) {
            ex.printStackTrace();
            commonResponse.setStatus(HttpStatus.EXPECTATION_FAILED.value());
            commonResponse.setErrorMessages(Collections.singletonList(ex.getMessage()));
            log.error("Error occurred while calling the save And Send Email For New Schedule  Method : " + ex.getMessage());
            return newSchedule;
        }
    }

    @GetMapping("/newSchedule/{userId}")
    public ResponseEntity<CommonResponse> getAllNewScheduleByUserId(@PathVariable("userId") String userId) {
        CommonResponse commonResponse = new CommonResponse();
        try {
            List<NewSchedule> vehicleDTOS = newScheduleService.getAllNewScheduled(userId);
            commonResponse.setPayload(Collections.singletonList(vehicleDTOS));
            return new ResponseEntity<>(commonResponse, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            commonResponse.setStatus(HttpStatus.EXPECTATION_FAILED.value());
            commonResponse.setErrorMessages(Collections.singletonList(e.getMessage()));
            log.error(e.getMessage());
            return new ResponseEntity<>(commonResponse, HttpStatus.EXPECTATION_FAILED);
        }
    }

    @PutMapping("/confirmSchedule/{requestId}")
    public ResponseEntity<CommonResponse> confirmAndMakePayment(@PathVariable("requestId") Integer requestId) {
        CommonResponse commonResponse = new CommonResponse();
        System.out.println("reqid :"+requestId);
        try {
            String updated= newScheduleService.confirmAndMakePayment(requestId);
            commonResponse.setPayload(Collections.singletonList(updated));
            return new ResponseEntity<>(commonResponse, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            commonResponse.setStatus(HttpStatus.EXPECTATION_FAILED.value());
            commonResponse.setErrorMessages(Collections.singletonList(e.getMessage()));
            log.error(e.getMessage());
            return new ResponseEntity<>(commonResponse, HttpStatus.EXPECTATION_FAILED);
        }
    }

}
