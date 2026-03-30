package com.lms.demo.dto;

import lombok.Data;

@Data
public class EmployeeResponseDTO
{
    private Long id;
    private String name;
    private String email;
    private String role;
}
