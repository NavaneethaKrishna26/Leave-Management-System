package com.lms.demo.dto.employeedto;

import lombok.Data;

@Data
public class EmployeeResponseDTO
{
    private Long id;
    private String name;
    private String email;
    private String role;
}
