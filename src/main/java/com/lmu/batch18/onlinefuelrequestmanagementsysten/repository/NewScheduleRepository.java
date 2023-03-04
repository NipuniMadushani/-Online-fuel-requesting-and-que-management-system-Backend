package com.lmu.batch18.onlinefuelrequestmanagementsysten.repository;

import com.lmu.batch18.onlinefuelrequestmanagementsysten.models.NewSchedule;
import com.lmu.batch18.onlinefuelrequestmanagementsysten.security.services.UserDetailsServiceImpl;
import com.lmu.batch18.onlinefuelrequestmanagementsysten.serviceImpl.FuelRequestServiceIMPL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NewScheduleRepository extends JpaRepository<NewSchedule, Integer> {
    @Query(value = "select * from new_schedule where user_id=?1 and schedule_state=false", nativeQuery = true)
    List<NewSchedule> findByUserId(String userId);
}
