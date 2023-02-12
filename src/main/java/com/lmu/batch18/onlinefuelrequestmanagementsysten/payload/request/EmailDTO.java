package com.lmu.batch18.onlinefuelrequestmanagementsysten.payload.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmailDTO {
    String to;
    String content;
    private Map< String, Object > model;
}
