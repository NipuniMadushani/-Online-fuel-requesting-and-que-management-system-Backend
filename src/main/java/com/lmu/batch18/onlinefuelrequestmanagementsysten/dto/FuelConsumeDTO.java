package com.lmu.batch18.onlinefuelrequestmanagementsysten.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FuelConsumeDTO {

    private double fullDieselStock;
    private double remainingDiesel;
    private double fullPetrolStock;
    private double remainingPetrol;
    private double consumeDiesel;
    private double consumePetrol;
    private double pricePerLiter;

}
