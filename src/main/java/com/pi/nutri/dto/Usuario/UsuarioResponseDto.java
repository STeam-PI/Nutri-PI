package com.pi.nutri.dto.Usuario;

import lombok.Data;

@Data
public class UsuarioResponseDto {
    private Long id;

    private String nome;
    
    private String email;

    private boolean isNutri;

    private String crn;
}
