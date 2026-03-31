package com.lms.demo.service;

import com.lms.demo.dto.employeedto.EmployeeResponseDTO;
import com.lms.demo.entity.Employee;
import com.lms.demo.repository.EmployeeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    // 🔍 Get logged-in user profile
    public EmployeeResponseDTO getProfile(String email) {

        Employee emp = employeeRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return mapToDTO(emp);
    }

    // 👥 Get team (only manager)
    public List<EmployeeResponseDTO> getTeam(String email) {

        Employee manager = employeeRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<Employee> team = employeeRepository.findByManager(manager);

        return team.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    // 🔁 Mapping
    private EmployeeResponseDTO mapToDTO(Employee emp) {
        EmployeeResponseDTO dto = new EmployeeResponseDTO();
        dto.setId(emp.getId());
        dto.setName(emp.getName());
        dto.setEmail(emp.getEmail());
        dto.setRole(emp.getRole().name());
        return dto;
    }
}