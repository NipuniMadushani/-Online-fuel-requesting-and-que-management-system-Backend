package com.lmu.batch18.onlinefuelrequestmanagementsysten.dto;

import com.lmu.batch18.onlinefuelrequestmanagementsysten.models.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FuelStationDTO {
    private int id;
    private String name;
    private String location;
    private boolean activeState;
    private User user;
    private int userId;

    public FuelStationDTO(int id, String name, String location, boolean activeState, User user) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.activeState = activeState;
        this.user = user;
    }

    public FuelStationDTO(int id, String name, String location, boolean activeState, int userId) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.activeState = activeState;
        this.userId = userId;
    }
}
