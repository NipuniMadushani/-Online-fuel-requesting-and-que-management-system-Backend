package com.lmu.batch18.onlinefuelrequestmanagementsysten.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "eligible_quata")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class EligibleQuota {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "fuel_type",length = 100)
    private String fuelType;


    @Column(name = "vehicle_type",length = 100)
    private String vehicleType;

    @Column(name = "quota",length = 100)
    private double quota;


}
