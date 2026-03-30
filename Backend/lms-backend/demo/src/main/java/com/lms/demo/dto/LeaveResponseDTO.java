package com.lms.demo.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class LeaveResponseDTO
{
    private Long id;

    private LocalDate fromDate;
    private LocalDate toDate;

    private String reason;
    private String status;

    private String employeeName;

    private String reviewedBy;
}
