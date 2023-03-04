package com.lmu.batch18.onlinefuelrequestmanagementsysten.service;

import com.lmu.batch18.onlinefuelrequestmanagementsysten.models.NewSchedule;

public interface NewScheduleService {

    NewSchedule saveAndSendEmailForNewSchedule(NewSchedule newSchedule);
}
