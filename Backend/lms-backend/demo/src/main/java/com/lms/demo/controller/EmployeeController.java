package com.lms.demo.controller;

import com.lms.demo.dto.ApiResponse;
import com.lms.demo.dto.employeedto.EmployeeResponseDTO;
import com.lms.demo.service.EmployeeService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    // GET /api/employees/profile
    @PreAuthorize("hasRole('EMPLOYEE') or hasRole('MANAGER')")
    @GetMapping("/profile")
    public ResponseEntity<ApiResponse<EmployeeResponseDTO>> getProfile(
            Authentication auth) {

        EmployeeResponseDTO profile = employeeService.getProfile(auth.getEmail());

        return ResponseEntity.ok(
                new ApiResponse<>(true, profile, "Profile fetched")
        );
    }

    // GET /api/employees/team
    @PreAuthorize("hasRole('MANAGER')")
    @GetMapping("/team")
    public ResponseEntity<ApiResponse<List<EmployeeResponseDTO>>> getTeam(
            Authentication auth) {

        List<EmployeeResponseDTO> team = employeeService.getTeam(auth.getEmail());

        return ResponseEntity.ok(
                new ApiResponse<>(true, team, "Team fetched")
        );
    }
}