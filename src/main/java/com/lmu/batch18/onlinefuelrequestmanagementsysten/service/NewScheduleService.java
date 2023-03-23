package com.lmu.batch18.onlinefuelrequestmanagementsysten.service;

import com.lmu.batch18.onlinefuelrequestmanagementsysten.models.NewSchedule;

import java.util.List;

public interface NewScheduleService {

    NewSchedule saveAndSendEmailForNewSchedule(NewSchedule newSchedule);

    List<NewSchedule> getAllNewScheduled(String userId);

    String confirmAndMakePayment(Integer requestId);
}
