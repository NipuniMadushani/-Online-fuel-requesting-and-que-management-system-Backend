package com.lmu.batch18.onlinefuelrequestmanagementsysten.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "fuel_station")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class FuelStation {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(length = 100)
    private String ownerName;

    @Column(length = 100)
    private String displayName;
    private String nearByTown;
    private String location;
    @Column(name = "active_state",columnDefinition = "TINYINT default 1")
    private boolean activeState;

    private String managerFirstName;
    private String managerLastName;
    private String managerEmail;
    private String managerContactNumber;


    private Integer petrolStock;

    private Integer remainingPetrolStock;

    private Integer dieselStock;

    private Integer remainingDieselStock;
    private int createdBy;


    @JsonIgnore
    @OneToMany(mappedBy="fuelStation")
    private Set<FuelRequest> fuelRequest;

    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    private Date createdDate;

    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    private Date updatedDate;


    @JsonIgnore
    @OneToMany(mappedBy="customer")
    private Set<FuelRequestHistory> fuelRequestHistory;





}
