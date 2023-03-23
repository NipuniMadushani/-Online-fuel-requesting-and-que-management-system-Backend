package com.lmu.batch18.onlinefuelrequestmanagementsysten.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EligibleQuotaDTO {
    private int id;
    private String fuelType;
    private String vehicleType;
    private double quota;

}
