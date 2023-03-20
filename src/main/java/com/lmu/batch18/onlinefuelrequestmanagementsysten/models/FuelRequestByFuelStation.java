package com.lmu.batch18.onlinefuelrequestmanagementsysten.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "fuel_request_by_fuel_station")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class FuelRequestByFuelStation {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "fuel_amount",length = 100)
    private Double fuelAmount;

    //    @JsonFormat(pattern = "dd-MM-yyyy")
    @Column(name = "requested_date",length = 100)
    private Date requestedDate;

    @Column(name = "approval_state")
    private boolean approvalState;

    @Column(name = "active_state",columnDefinition = "TINYINT default 0")
    private boolean activeState;

    @Column(name = "actual_quata",length = 100)
    private Double actualQuata;


    //    @ManyToOne
//    @JoinColumn(name="customer_id", nullable=false)
//    private User customer;
    private int userId;

    @ManyToOne
    @JoinColumn(name="fuel_station_id", nullable=false)
    private FuelStation fuelStation;

    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    private Date createdDate;

    private String modifiedBy;

    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    private Date updatedDate;

    @DateTimeFormat(pattern="hh:mm a")
    private Date scheduleTime;

    private boolean rejectState;

    public FuelRequestByFuelStation(int id, Double fuelAmount, FuelStation fuelStation) {
        this.id = id;
        this.fuelAmount = fuelAmount;
        this.fuelStation = fuelStation;
    }
}
