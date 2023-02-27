package com.lmu.batch18.onlinefuelrequestmanagementsysten.shedulers;

import com.lmu.batch18.onlinefuelrequestmanagementsysten.models.FuelRequest;
import com.lmu.batch18.onlinefuelrequestmanagementsysten.models.FuelRequestHistory;
import com.lmu.batch18.onlinefuelrequestmanagementsysten.models.WeekDetails;
import com.lmu.batch18.onlinefuelrequestmanagementsysten.repository.FuelRequestHistoryRepository;
import com.lmu.batch18.onlinefuelrequestmanagementsysten.repository.FuelRequestRepository;
import com.lmu.batch18.onlinefuelrequestmanagementsysten.repository.WeekDetailsRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import javax.transaction.Transactional;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Configuration
@EnableScheduling
public class WeekEndTask {
    @Autowired
    private FuelRequestRepository fuelRequestRepository;

    @Autowired
    private FuelRequestHistoryRepository fuelRequestHistoryRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private WeekDetailsRepository weekDetailsRepository;

    @Scheduled(cron = "0 0 0 * * SUN")
    //@Scheduled(fixedRate = 100000)
    @Transactional
    public void scheduleUpdateRequestHistory() {
        List<FuelRequest> fuelRequest = fuelRequestRepository.findAll();
        List<FuelRequestHistory> fuelRequestHistories  = modelMapper.
                map(fuelRequest, new TypeToken<List<FuelRequestHistory>>() {
                }.getType());

        fuelRequestHistoryRepository.saveAll(fuelRequestHistories);
        fuelRequestRepository.deleteAll();

        Calendar c = Calendar.getInstance();
        c.setTime(new Date()); // Using today's date
        c.add(Calendar.DATE, 7);

        WeekDetails weekDetails = new WeekDetails(
                1,
                new Date(),
                c.getTime()
        );
        weekDetailsRepository.deleteAll();
        weekDetailsRepository.save(weekDetails);
    }
}
