package com.lmu.batch18.onlinefuelrequestmanagementsysten.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FuelRequestActualQuotaCountDTO {
    private int fuelStationId;
    private double petrolActualQuotaCount;
    private double dieselActualQuotaCount;
}
