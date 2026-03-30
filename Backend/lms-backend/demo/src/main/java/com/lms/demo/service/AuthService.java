package com.lms.demo.service;

import com.lms.demo.dto.authdto.AuthRequestDTO;
import com.lms.demo.dto.authdto.AuthResponseDTO;
import com.lms.demo.repository.EmployeeRepository;
import jakarta.validation.Valid;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final EmployeeRepository employeeRepository;
    private final PasswordEncoder encoder;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authManager;
    public AuthService(EmployeeRepository employeeRepository, PasswordEncoder encoder,
                       JwtUtil jwtUtil, AuthenticationManager authManager) {
        this.employeeRepository = employeeRepository;

        this.encoder = encoder;
        this.jwtUtil = jwtUtil;
        this.authManager = authManager;
    }

    public AuthResponseDTO login(@Valid AuthRequestDTO req) {
        authManager.authenticate(
                new UsernamePasswordAuthenticationToken(req.getEmail(),
                        req.getPassword()));
        return new AuthResponseDTO(jwtUtil.generateToken(req.getEmail()));
    }
}