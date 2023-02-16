package com.lmu.batch18.onlinefuelrequestmanagementsysten.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "fuel_request")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class FuelRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "fuel_amount",length = 100)
    private Double fuelAmount;

    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    @Column(name = "requested_date",length = 100)
    private Date requestedDate;

    @Column(name = "approval_state",columnDefinition = "TINYINT default 0")
    private boolean approval_state;

    @Column(name = "sheduled_state",columnDefinition = "TINYINT default 0")
    private boolean sheduledState;

    @Column(name = "active_state",columnDefinition = "TINYINT default 0")
    private boolean activeState;

    @Column(name = "eligible_quata",length = 100)
    private Double eligibleQuata;

    @Column(name = "actual_quata",length = 100)
    private Double actualQuata;

    @Column(name = "vehicleType",length = 100)
    private String vehicleType;

    @ManyToOne
    @JoinColumn(name="customer_id", nullable=false)
    private Customer customer;

    @ManyToOne
    @JoinColumn(name="fuel_station_id", nullable=false)
    private FuelStation fuelStation;








}