package com.lms.demo.dto.authdto;

import lombok.Data;

@Data
public class AuthResponseDTO{
    private String token;
    private String role;
}
