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

    private final JwtUtil jwtUtil;
    private final AuthenticationManager authManager;
    public AuthService(JwtUtil jwtUtil, AuthenticationManager authManager) {
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