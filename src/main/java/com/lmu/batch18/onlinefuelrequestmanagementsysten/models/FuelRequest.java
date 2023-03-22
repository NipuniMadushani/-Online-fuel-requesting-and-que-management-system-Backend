package com.lmu.batch18.onlinefuelrequestmanagementsysten.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "fuel_request")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class FuelRequest {
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

    @Column(name = "sheduled_state",columnDefinition = "TINYINT default 0")
    private boolean sheduledState;

    @Column(name = "active_state",columnDefinition = "TINYINT default 0")
    private boolean activeState;

    @Column(name = "eligible_quata",length = 100)
    private Double eligibleQuata;

    @Column(name = "actual_quata",length = 100)
    private Double actualQuata;

    //should be vehicle id
    @Column(name = "vehicleType",length = 100)
    private String vehicleType;

//    @ManyToOne
//    @JoinColumn(name="customer_id", nullable=false)
//    private User customer;
     private int userId;

    @ManyToOne
    @JoinColumn(name="fuel_station_id", nullable=false)
    private FuelStation fuelStation;

    @ManyToOne
    @JoinColumn(name="vehicle_id", nullable=false)
    private Vehicle vehicle;

    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    private Date createdDate;

    private String modifiedBy;

    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    private Date updatedDate;

    @DateTimeFormat(pattern="hh:mm a")
    private Date scheduleTime;

    private boolean rejectState;

    @Column(name = "consume_state",columnDefinition = "TINYINT default 0")
    private boolean consumedState;

    @Column(name = "fuelType",length = 100)
    private String fuelType;




    public FuelRequest(int id, Double fuelAmount, Date requestedDate, Double actualQuata, FuelStation fuelStation, Vehicle vehicle, boolean consumedState,String fuelType) {
        this.id = id;
        this.fuelAmount = fuelAmount;
        this.requestedDate = requestedDate;
        this.actualQuata = actualQuata;
        this.fuelStation = fuelStation;
        this.vehicle = vehicle;
        this.consumedState = consumedState;
        this.fuelType = fuelType;
    }
}
