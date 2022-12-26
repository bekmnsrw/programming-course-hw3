package com.example.hw3.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Builder
@Data
public class Doctor {
    private Long id;
    private String firstName;
    private String secondName;
    private String speciality;
}
