package com.lmu.batch18.onlinefuelrequestmanagementsysten.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lmu.batch18.onlinefuelrequestmanagementsysten.models.FuelStation;
import com.lmu.batch18.onlinefuelrequestmanagementsysten.models.Vehicle;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FuelRequestDTO {
    private int id;
    private Double fuelAmount;
    @JsonFormat(pattern = "dd-MM-yyyy")
    private Date requestedDate;
    private boolean approval_state;
    private boolean sheduledState;
    private boolean activeState;
    private Double eligibleQuata;
    private Double actualQuata;
    private String vehicleType;
//    private User customer;
    private FuelStation fuelStation;
    private Vehicle vehicle;
    private int customerId;
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    private Date createdDate;

    private String modifiedBy;

    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    private Date updatedDate;

    @DateTimeFormat(pattern="hh:mm a")
    private Date scheduleTime;


    public FuelRequestDTO(int id, Date requestedDate, String vehicleType, Double eligibleQuata, Double actualQuata, boolean approval_state) {
    this.id=id;
    this.requestedDate=requestedDate;
    this.vehicleType=vehicleType;
    this.eligibleQuata=eligibleQuata;
    this.actualQuata=actualQuata;
    this.approval_state=approval_state;
    }

    public FuelRequestDTO(int id, Date requestedDate, String vehicleType, Double eligibleQuata, Double actualQuata, boolean approval_state, Date scheduleTime, FuelStation fuelStation) {
        this.id=id;
        this.requestedDate=requestedDate;
        this.vehicleType=vehicleType;
        this.eligibleQuata=eligibleQuata;
        this.actualQuata=actualQuata;
        this.approval_state=approval_state;
        this.scheduleTime=scheduleTime;
        this.fuelStation=fuelStation;
    }

//    public FuelRequestDTO(int id, Date requestedDate, String vehicleType, FuelStation fuelStation, Double actualQuata, boolean approval_state) {
//
//    }

//    private int fuelStationId;
//    private int vehicleId;

}
