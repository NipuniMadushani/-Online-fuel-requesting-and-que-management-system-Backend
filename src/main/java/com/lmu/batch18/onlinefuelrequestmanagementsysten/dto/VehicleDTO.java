package com.lmu.batch18.onlinefuelrequestmanagementsysten.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VehicleDTO {

    private int id;
    private String vehicleNumber;
    private String chassisNumber;
    private String vehicleType;
    private String fuelType;
    private String createdBy;

    private String userId;

    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    private Date createdDate;

    private String modifiedBy;

    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    private Date updatedDate;


    public VehicleDTO(int id, String vehicleNumber, String chassisNumber, String vehicleType, String fuelType) {
    this.id=id;
    this.vehicleNumber=vehicleNumber;
    this.chassisNumber=chassisNumber;
    this.vehicleType=vehicleType;
    this.fuelType=fuelType;
    }
}
