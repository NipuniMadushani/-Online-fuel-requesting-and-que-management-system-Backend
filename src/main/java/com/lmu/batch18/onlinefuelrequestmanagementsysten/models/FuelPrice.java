package com.lmu.batch18.onlinefuelrequestmanagementsysten.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "fuel_price")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class FuelPrice {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "fuel_type",length = 100)
    private String fuelType;

    @Column(name = "price_per_liter",length = 100)
    private double pricePerLiter;

}
