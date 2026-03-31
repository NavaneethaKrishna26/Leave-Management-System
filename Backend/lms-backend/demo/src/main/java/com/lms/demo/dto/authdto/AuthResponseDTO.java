package com.lms.demo.dto.authdto;

import lombok.Data;

@Data
public class AuthResponseDTO{
    private String token;

    public AuthResponseDTO(String s) {
        this.token=s;
    }
}
