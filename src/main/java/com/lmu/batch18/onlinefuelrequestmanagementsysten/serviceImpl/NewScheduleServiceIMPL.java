package com.lmu.batch18.onlinefuelrequestmanagementsysten.serviceImpl;

import com.lmu.batch18.onlinefuelrequestmanagementsysten.models.NewSchedule;
import com.lmu.batch18.onlinefuelrequestmanagementsysten.models.User;
import com.lmu.batch18.onlinefuelrequestmanagementsysten.repository.NewScheduleRepository;
import com.lmu.batch18.onlinefuelrequestmanagementsysten.repository.UserRepository;
import com.lmu.batch18.onlinefuelrequestmanagementsysten.security.services.UserDetailsServiceImpl;
import com.lmu.batch18.onlinefuelrequestmanagementsysten.service.NewScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NewScheduleServiceIMPL implements NewScheduleService {
    @Autowired
    UserDetailsServiceImpl userDetailsService;
    @Autowired
    NewScheduleRepository newScheduleRepository;
    @Autowired
    FuelRequestServiceIMPL fuelRequestServiceIMPL;
    @Autowired
    UserRepository userRepository;

    @Override
    public NewSchedule saveAndSendEmailForNewSchedule(NewSchedule newSchedule) {
        NewSchedule newSchedule1 = new NewSchedule();
        if (newSchedule.getUserId() != 0) {
            User user = userRepository.findById(newSchedule.getUserId()).get();
            if (user != null) {
                String status = userDetailsService.sendEmailWithNewSchedule(newSchedule, user);
                if (status.equals("SUCCESSFULL")) {
                    newSchedule1 = newScheduleRepository.save(newSchedule);
                    fuelRequestServiceIMPL.rejectFuelRequestsByRequestId(newSchedule.getFuelRequest().getId());
                }
            }
        }
        return newSchedule1;
    }

    @Override
    public List<NewSchedule> getAllNewScheduled(String userId) {
        return newScheduleRepository.findByUserId(userId);
    }
}
