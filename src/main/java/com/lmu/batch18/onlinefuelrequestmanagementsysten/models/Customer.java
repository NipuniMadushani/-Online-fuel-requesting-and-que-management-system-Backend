package com.lmu.batch18.onlinefuelrequestmanagementsysten.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "customer")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "name",length = 100)
    private String name;

    @Column(name = "nic",length = 100)
    private String nic;

    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    @Column(name = "birthday",length = 100)
    private Date birthdate;

    @Column(name = "active_state",columnDefinition = "TINYINT default 1")
    private boolean activeState;

    @Column(name = "address",length = 100)
    private String address;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

//    @JsonIgnore
//    @OneToMany(mappedBy="customer")
//    private Set<FuelRequest> fuelRequest;

    @JsonIgnore
    @OneToMany(mappedBy="customer")
    private Set<FuelRequestHistory> fuelRequestHistory;




    public Customer(int id, String name, String nic, Date birthdate, String address, User user) {
        this.id = id;
        this.name = name;
        this.nic = nic;
        this.birthdate = birthdate;
        this.address = address;
        this.user = user;
    }
}
