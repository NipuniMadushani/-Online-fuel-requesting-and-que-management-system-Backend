package com.lmu.batch18.onlinefuelrequestmanagementsysten.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
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

    @Column(name = "name",length = 100)
    private String name;

    @Column(name = "location",length = 100)
    private String location;

    @Column(name = "active_state",columnDefinition = "TINYINT default 1")
    private boolean activeState;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "created_user_id", referencedColumnName = "id")
    private User user;

    @JsonIgnore
    @OneToMany(mappedBy="fuelStation")
    private Set<FuelRequest> fuelRequest;


    @JsonIgnore
    @OneToMany(mappedBy="customer")
    private Set<FuelRequestHistory> fuelRequestHistory;





}
