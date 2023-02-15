package com.lmu.batch18.onlinefuelrequestmanagementsysten.dto;

import com.lmu.batch18.onlinefuelrequestmanagementsysten.models.Customer;
import com.lmu.batch18.onlinefuelrequestmanagementsysten.models.FuelStation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FuelRequestDTO {
    private int id;
    private Double fuelAmount;
    private Date requestedDate;
    private boolean approval_state;
    private boolean sheduledState;
    private boolean activeState;
    private Double eligibleQuata;
    private Double actualQuata;
    private String vehicleType;
    private Customer customer;
    private FuelStation fuelStation;
    private int customerId;
    private int fuelStationId;



}
