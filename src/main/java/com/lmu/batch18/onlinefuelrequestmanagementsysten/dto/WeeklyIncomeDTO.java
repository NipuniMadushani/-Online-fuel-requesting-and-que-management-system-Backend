package com.lmu.batch18.onlinefuelrequestmanagementsysten.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class WeeklyIncomeDTO {

    private String requestedDate;

    private Double dailyIncome;


}
