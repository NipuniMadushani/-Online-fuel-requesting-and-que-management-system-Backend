package com.lmu.batch18.onlinefuelrequestmanagementsysten.repository;

import com.lmu.batch18.onlinefuelrequestmanagementsysten.models.NewSchedule;
import com.lmu.batch18.onlinefuelrequestmanagementsysten.security.services.UserDetailsServiceImpl;
import com.lmu.batch18.onlinefuelrequestmanagementsysten.serviceImpl.FuelRequestServiceIMPL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface NewScheduleRepository extends JpaRepository<NewSchedule, Integer> {
    @Query(value = "select * from new_schedule where user_id=?1 and confirm_state=false", nativeQuery = true)
    List<NewSchedule> findByUserId(String userId);


    @Modifying
    @Transactional
    @Query(value = "update new_schedule set confirm_state=true and is_paid=true where id=?1",nativeQuery = true)
    void confirmNewSchedule(Integer requestId);
}
