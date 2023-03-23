package com.lmu.batch18.onlinefuelrequestmanagementsysten.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FuelStationDTO {
    private int id;
    private String ownerName;

    private String displayName;

    private String nearByTown;
    private String location;
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

    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    private Date createdDate;

    private String modifiedBy;

    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    private Date updatedDate;
}
