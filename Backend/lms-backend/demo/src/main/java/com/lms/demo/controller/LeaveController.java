package com.lms.demo.controller;

import com.lms.demo.security.CustomUserDetails;
import com.lms.demo.dto.ApiResponse;
import com.lms.demo.dto.leavedto.LeaveRequestDTO;
import com.lms.demo.dto.leavedto.LeaveResponseDTO;
import com.lms.demo.service.LeaveService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
            @AuthenticationPrincipal CustomUserDetails userDetails) {

        String email = userDetails.getUsername();
        LeaveResponseDTO response = leaveService.applyLeave(request, email);

        return ResponseEntity.ok(
                new ApiResponse<>(true, response, "Leave applied successfully")
        );
    }

    // GET /api/leaves/my
    @PreAuthorize("hasRole('EMPLOYEE') or hasRole('MANAGER')")
    @GetMapping("/my")
    public ResponseEntity<ApiResponse<List<LeaveResponseDTO>>> getMyLeaves(
            @AuthenticationPrincipal CustomUserDetails userDetails) {

        String email = userDetails.getUsername();
        List<LeaveResponseDTO> leaves = leaveService.getMyLeaves(email);

        return ResponseEntity.ok(
                new ApiResponse<>(true, leaves, "My leaves fetched")
        );
    }

    // GET /api/leaves/team
    @PreAuthorize("hasRole('MANAGER')")
    @GetMapping("/team")
    public ResponseEntity<ApiResponse<List<LeaveResponseDTO>>> getTeamLeaves(
            @AuthenticationPrincipal CustomUserDetails userDetails) {

        String email = userDetails.getUsername();
        List<LeaveResponseDTO> leaves = leaveService.getTeamLeaves(email);

        return ResponseEntity.ok(
                new ApiResponse<>(true, leaves, "Team leaves fetched")
        );
    }

    // PUT /api/leaves/{id}/approve
    @PreAuthorize("hasRole('MANAGER')")
    @PutMapping("/{id}/approve")
    public ResponseEntity<ApiResponse<LeaveResponseDTO>> approveLeave(
            @PathVariable Long id,
            @AuthenticationPrincipal CustomUserDetails userDetails) {
        String email = userDetails.getUsername();
        LeaveResponseDTO response = leaveService.approveLeave(id, email);

        return ResponseEntity.ok(
                new ApiResponse<>(true, response, "Leave approved")
        );
    }

    // PUT /api/leaves/{id}/reject
    @PreAuthorize("hasRole('MANAGER')")
    @PutMapping("/{id}/reject")
    public ResponseEntity<ApiResponse<LeaveResponseDTO>> rejectLeave(
            @PathVariable Long id,
            @AuthenticationPrincipal CustomUserDetails userDetails) {
        String email = userDetails.getUsername();
        LeaveResponseDTO response = leaveService.rejectLeave(id, email);

        return ResponseEntity.ok(
                new ApiResponse<>(true, response, "Leave rejected")
        );
    }
}