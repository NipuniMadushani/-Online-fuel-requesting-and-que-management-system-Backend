package com.lmu.batch18.onlinefuelrequestmanagementsysten.repository;


import com.lmu.batch18.onlinefuelrequestmanagementsysten.dto.WeeklyIncomeDTO;
import com.lmu.batch18.onlinefuelrequestmanagementsysten.models.FuelRequest;
import com.lmu.batch18.onlinefuelrequestmanagementsysten.models.FuelStation;
import com.lmu.batch18.onlinefuelrequestmanagementsysten.models.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

@Repository
public interface FuelRequestRepository extends JpaRepository<FuelRequest, Integer> {

    //    List<FuelRequest> findByCustomerEquals(Customer u);
    List<FuelRequest> findAllByVehicleEquals(Vehicle vehicle);

    @Query(
            value = "select * from fuel_request where user_id=?1 order by CASE WHEN updated_date > created_date THEN updated_date\n" +
                    "        ELSE created_date\n" +
                    "   END  desc ",
            nativeQuery = true
    )
    @Transactional
    List<FuelRequest> findByUserId(int customerId);

    @Query(
            value = "select * from fuel_request where id=?1",
            nativeQuery = true
    )
    @Transactional
    List<FuelRequest> findByRequestId(int requestId);

    @Transactional
    @Modifying
    @Query(
            value = "UPDATE fuel_request\n" +
                    "SET approval_state = true WHERE id=?1",
            nativeQuery = true
    )
    void updateApproveById(int fuelRequestId);


    @Transactional
    @Modifying
    @Query(
            value = "UPDATE fuel_request\n" +
                    "SET reject_state = true WHERE id=?1",
            nativeQuery = true
    )
    void updateRejectStatusById(int fuelRequestId);

    @Transactional
    @Modifying
    @Query(
            value = "SELECT DATE_FORMAT(requested_date, '%Y-%m-%d') as requested_date,SUM(fuel_amount) as daily_income\n" +
                    "FROM fuel_request \n" +
                    "GROUP BY DATE_FORMAT(requested_date, '%Y-%m-%d')",
            nativeQuery = true
    )
    List getAllWeeklyIncome();

    @Transactional
    @Modifying
    @Query(value = "SELECT DATE_FORMAT(requested_date, '%Y-%m-%d') as requested_date,count(id) as daily_approval_quota " +
            "FROM fuel_request  where approval_state=true and DATE_FORMAT(requested_date, '%Y-%m-%d') GROUP BY DATE_FORMAT(requested_date, '%Y-%m-%d')", nativeQuery = true)
    List<Object[]> allTokenRequest();

    @Transactional
    @Modifying
    @Query(value = "update fuel_request  set consume_state=true where id=?1",nativeQuery = true)
    void updateConsumeStateForFuelRequest(int id);

    List<FuelRequest> findAllByFuelStationAndConsumedState(FuelStation fuelStation, boolean b);
}
