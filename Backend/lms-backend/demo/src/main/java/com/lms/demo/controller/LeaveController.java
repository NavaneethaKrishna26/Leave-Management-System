package com.lms.demo.controller;

import com.lms.demo.dto.ApiResponse;
import com.lms.demo.dto.leavedto.LeaveRequestDTO;
import com.lms.demo.dto.leavedto.LeaveResponseDTO;
import com.lms.demo.service.LeaveService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/leaves")
public class LeaveController {

    private final LeaveService leaveService;

    public LeaveController(LeaveService leaveService) {
        this.leaveService = leaveService;
    }

    // POST /api/leaves/apply
    @PreAuthorize("hasRole('EMPLOYEE') or hasRole('MANAGER')")
    @PostMapping("/apply")
    public ResponseEntity<ApiResponse<LeaveResponseDTO>> applyLeave(
            @Valid @RequestBody LeaveRequestDTO request,
            Authentication auth) {

        LeaveResponseDTO response = leaveService.applyLeave(request, auth.getEmail());

        return ResponseEntity.ok(
                new ApiResponse<>(true, response, "Leave applied successfully")
        );
    }

    // GET /api/leaves/my
    @PreAuthorize("hasRole('EMPLOYEE') or hasRole('MANAGER')")
    @GetMapping("/my")
    public ResponseEntity<ApiResponse<List<LeaveResponseDTO>>> getMyLeaves(
            Authentication auth) {

        List<LeaveResponseDTO> leaves = leaveService.getMyLeaves(auth.getEmail());

        return ResponseEntity.ok(
                new ApiResponse<>(true, leaves, "My leaves fetched")
        );
    }

    // GET /api/leaves/team
    @PreAuthorize("hasRole('MANAGER')")
    @GetMapping("/team")
    public ResponseEntity<ApiResponse<List<LeaveResponseDTO>>> getTeamLeaves(
            Authentication auth) {

        List<LeaveResponseDTO> leaves = leaveService.getTeamLeaves(auth.getEmail());

        return ResponseEntity.ok(
                new ApiResponse<>(true, leaves, "Team leaves fetched")
        );
    }

    // PUT /api/leaves/{id}/approve
    @PreAuthorize("hasRole('MANAGER')")
    @PutMapping("/{id}/approve")
    public ResponseEntity<ApiResponse<LeaveResponseDTO>> approveLeave(
            @PathVariable Long id,
            Authentication auth) {

        LeaveResponseDTO response = leaveService.approveLeave(id, auth.getName());

        return ResponseEntity.ok(
                new ApiResponse<>(true, response, "Leave approved")
        );
    }

    // PUT /api/leaves/{id}/reject
    @PreAuthorize("hasRole('MANAGER')")
    @PutMapping("/{id}/reject")
    public ResponseEntity<ApiResponse<LeaveResponseDTO>> rejectLeave(
            @PathVariable Long id,
            Authentication auth) {

        LeaveResponseDTO response = leaveService.rejectLeave(id, auth.getName());

        return ResponseEntity.ok(
                new ApiResponse<>(true, response, "Leave rejected")
        );
    }
}