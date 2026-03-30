package com.lms.demo.dto.leavedto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.LocalDate;

@Data
public class LeaveRequestDTO
{
    @NotBlank(message = "From date should not be empty")
    private LocalDate fromDate;
    @NotBlank(message = "To date should not be empty")
    private LocalDate toDate;
    @NotBlank(message = "reason should not be empty")
    private String reason;
}
