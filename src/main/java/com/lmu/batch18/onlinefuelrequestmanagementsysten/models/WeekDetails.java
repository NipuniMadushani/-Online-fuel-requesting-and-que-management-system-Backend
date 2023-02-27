package com.lmu.batch18.onlinefuelrequestmanagementsysten.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "week_details")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class WeekDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "week_start_date")
    private Date startDate;

    @Column(name = "week_end_date")
    private Date endDate;

}
