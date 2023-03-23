package com.lmu.batch18.onlinefuelrequestmanagementsysten.dto;

import com.lmu.batch18.onlinefuelrequestmanagementsysten.models.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDTO {
    private int id;
    private String name;
    private String nic;
    private Date birthdate;
    private boolean activeState;
    private String address;
    private int userId;

    private User user;

    public CustomerDTO(int id, String name, String nic, Date birthdate, boolean activeState, String address, int userId) {
        this.id = id;
        this.name = name;
        this.nic = nic;
        this.birthdate = birthdate;
        this.activeState = activeState;
        this.address = address;
        this.userId = userId;
    }

    public CustomerDTO(int id, String name, String nic, Date birthdate, boolean activeState, String address, User user) {
        this.id = id;
        this.name = name;
        this.nic = nic;
        this.birthdate = birthdate;
        this.activeState = activeState;
        this.address = address;
        this.user = user;
    }
}
