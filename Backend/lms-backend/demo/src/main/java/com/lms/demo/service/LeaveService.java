package com.lms.demo.service;

import com.lms.demo.dto.leavedto.LeaveRequestDTO;
import com.lms.demo.dto.leavedto.LeaveResponseDTO;
import com.lms.demo.entity.Employee;
import com.lms.demo.entity.Leave;
import com.lms.demo.entity.Status;
import com.lms.demo.repository.EmployeeRepository;
import com.lms.demo.repository.LeaveRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LeaveService {

    private final LeaveRepository leaveRepository;
    private final EmployeeRepository employeeRepository;

    public LeaveService(LeaveRepository leaveRepository,
                        EmployeeRepository employeeRepository) {
        this.leaveRepository = leaveRepository;
        this.employeeRepository = employeeRepository;
    }

    // 📝 Apply leave
    public LeaveResponseDTO applyLeave(LeaveRequestDTO request, String email) {

        Employee emp = employeeRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Leave leave = new Leave();
        leave.setFromDate(request.getFromDate());
        leave.setToDate(request.getToDate());
        leave.setReason(request.getReason());
        leave.setStatus(Status.PENDING);
        leave.setEmployee(emp);

        Leave saved = leaveRepository.save(leave);

        return mapToDTO(saved);
    }

    // 📄 My leaves
    public List<LeaveResponseDTO> getMyLeaves(String email) {

        Employee emp = employeeRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return leaveRepository.findByEmployee(emp)
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    // 👥 Team leaves (manager)
    public List<LeaveResponseDTO> getTeamLeaves(String email) {

        Employee manager = employeeRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<Employee> team = employeeRepository.findByManager(manager);

        return leaveRepository.findByEmployeeIn(team)
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    // ✅ Approve leave
    public LeaveResponseDTO approveLeave(Long id, String email) {

        Leave leave = leaveRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Leave not found"));

        Employee manager = employeeRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        if (!leave.getEmployee().getManager().getId().equals(manager.getId())) {
            throw new RuntimeException("Not authorized");
        }
        leave.setStatus(Status.APPROVED);
        leave.setReviewedBy(manager);

        return mapToDTO(leaveRepository.save(leave));
    }

    // ❌ Reject leave
    public LeaveResponseDTO rejectLeave(Long id, String email) {

        Leave leave = leaveRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Leave not found"));

        Employee manager = employeeRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        if (!leave.getEmployee().getManager().getId().equals(manager.getId())) {
            throw new RuntimeException("Not authorized");
        }
        leave.setStatus(Status.REJECTED);
        leave.setReviewedBy(manager);

        return mapToDTO(leaveRepository.save(leave));
    }

    // 🔁 Mapping
    private LeaveResponseDTO mapToDTO(Leave leave) {

        LeaveResponseDTO dto = new LeaveResponseDTO();
        dto.setId(leave.getId());
        dto.setFromDate(leave.getFromDate());
        dto.setToDate(leave.getToDate());
        dto.setReason(leave.getReason());
        dto.setStatus(leave.getStatus().name());
        dto.setEmployeeName(leave.getEmployee().getName());
        if (leave.getReviewedBy() != null) dto.setReviewedBy(leave.getReviewedBy().getName());
        return dto;
    }
}
