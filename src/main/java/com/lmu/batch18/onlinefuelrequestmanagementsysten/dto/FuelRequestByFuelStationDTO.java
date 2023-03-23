package com.lmu.batch18.onlinefuelrequestmanagementsysten.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lmu.batch18.onlinefuelrequestmanagementsysten.models.FuelStation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FuelRequestByFuelStationDTO {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private Double fuelAmount;

    private Date requestedDate;

    private boolean approvalState;

    private boolean activeState;

    private Double actualQuata;

    private int userId;

    private FuelStation fuelStation;

    private Date createdDate;

    private String modifiedBy;

    private Date updatedDate;

    private Date scheduleTime;

    private boolean rejectState;

    private  String fuelType;
}
