package com.example.hw3.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Builder
@Data
public class Reception {
    private Long id;
    private String date;
    private String startTime;
    private String endTime;
    private String doctorSecondName;
    private String patientSecondName;
    private Long doctorId;
    private Long patientId;
}
