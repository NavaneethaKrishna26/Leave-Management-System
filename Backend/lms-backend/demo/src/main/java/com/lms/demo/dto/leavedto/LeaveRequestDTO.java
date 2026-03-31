package com.lms.demo.dto.leavedto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class LeaveRequestDTO
{
    @NotNull
    private LocalDate fromDate;
    @NotNull
    private LocalDate toDate;
    @NotBlank(message = "reason should not be empty")
    private String reason;
}
