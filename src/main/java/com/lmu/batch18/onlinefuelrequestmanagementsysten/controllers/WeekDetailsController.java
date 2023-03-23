package com.lmu.batch18.onlinefuelrequestmanagementsysten.controllers;

import com.lmu.batch18.onlinefuelrequestmanagementsysten.dto.CustomerDTO;
import com.lmu.batch18.onlinefuelrequestmanagementsysten.models.WeekDetails;
import com.lmu.batch18.onlinefuelrequestmanagementsysten.repository.WeekDetailsRepository;
import com.lmu.batch18.onlinefuelrequestmanagementsysten.util.CommonResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/auth/v1/week")
@CrossOrigin("*")
@Slf4j
public class WeekDetailsController {
    @Autowired
    private WeekDetailsRepository weekDetailsRepository;

    @GetMapping
    public ResponseEntity<CommonResponse> currentWeek() {
        CommonResponse commonResponse = new CommonResponse();
        try {
            WeekDetails weekDetails = weekDetailsRepository.findById(1).get();
            commonResponse.setPayload(Collections.singletonList(weekDetails));
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
