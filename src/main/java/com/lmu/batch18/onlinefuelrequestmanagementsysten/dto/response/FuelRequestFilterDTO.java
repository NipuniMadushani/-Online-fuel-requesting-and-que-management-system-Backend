package com.lmu.batch18.onlinefuelrequestmanagementsysten.dto.response;

import com.lmu.batch18.onlinefuelrequestmanagementsysten.models.Customer;
import com.lmu.batch18.onlinefuelrequestmanagementsysten.models.FuelStation;
import com.lmu.batch18.onlinefuelrequestmanagementsysten.models.Vehicle;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FuelRequestFilterDTO {
    private Double fuelAmount;
    private Double eligibleQuata;
    private Double actualQuata;
    private Double balanceQuata;
    private String vehicleType;
    private String fuelType;
    private double pricePerLiter;

    private Vehicle vehicle;

    private boolean consumedState;


}
