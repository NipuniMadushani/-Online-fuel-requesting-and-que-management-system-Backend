package com.lmu.batch18.onlinefuelrequestmanagementsysten.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Vehicle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String vehicleNumber;
    private String chassisNumber;
    private String vehicleType;
    private String fuelType;
    private String createdBy;
//    @ManyToOne
//    @JoinColumn(name = "user_id")
//    private  User userId;
    private String userId;

    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    private Date createdDate;

    private String modifiedBy;

    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    private Date updatedDate;


}
