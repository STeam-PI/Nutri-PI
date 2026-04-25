package com.pi.nutri.dto;

public record JwtResponseDTO(String token, String tipo) {

    public JwtResponseDTO(String token) {
        this(token, "Bearer");
    }
}
