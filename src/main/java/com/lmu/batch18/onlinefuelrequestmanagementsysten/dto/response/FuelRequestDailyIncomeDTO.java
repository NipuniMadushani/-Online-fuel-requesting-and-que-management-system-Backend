package com.lmu.batch18.onlinefuelrequestmanagementsysten.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FuelRequestDailyIncomeDTO {
    private int fuelStationId;
    private Date date;
    private double dailyIncome;
}
