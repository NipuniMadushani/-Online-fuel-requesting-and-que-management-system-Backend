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
@Table(name = "new_schedule")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class NewSchedule {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private int userId;
    @Column(name = "available_date", length = 100)
    private Date availableDate;
    @DateTimeFormat(pattern = "hh:mm a")
    private Date availableTime;
    private Double requestedQuota;
    @Column(name = "schedule_state", columnDefinition = "TINYINT default 1")
    private boolean scheduleState;
    @ManyToOne
    @JoinColumn(name = "vehicle_id", nullable = false)
    private Vehicle vehicle;
    @OneToOne
    @JoinColumn(name = "fuel_request_id")
    private FuelRequest fuelRequest;
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    private Date createdDate;
    private String modifiedBy;
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    private Date updatedDate;
    @Column(name = "confirm_state", columnDefinition = "TINYINT default 0")
    private boolean confirmState;
    @Column(name = "reject_state", columnDefinition = "TINYINT default 0")
    private boolean rejectState;

    private  boolean isPaid;

}
