package com.lmu.batch18.onlinefuelrequestmanagementsysten.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FuelPriceDTO {
    private int id;
    private String fuelType;
    private double pricePerLiter;
}
