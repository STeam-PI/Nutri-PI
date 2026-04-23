package com.pi.nutri.dto.Usuario;

import jakarta.persistence.Column;
import lombok.Data;

@Data
public class UsuarioRequestDto {
    private String nome;
    
    private String email;

    private String senha;

    private String confirmeSenha;

    private boolean isNutri;

    private String crn;
}
