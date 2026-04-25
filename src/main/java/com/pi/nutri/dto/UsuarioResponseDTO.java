package com.pi.nutri.dto;

import com.pi.nutri.model.Usuario;

public record UsuarioResponseDTO(Long id, String nome, String email, boolean isNutri, String crn) {

    public static UsuarioResponseDTO de(Usuario usuario) {
        return new UsuarioResponseDTO(
                usuario.getId(),
                usuario.getNome(),
                usuario.getEmail(),
                usuario.isNutri(),
                usuario.getCrn()
        );
    }
}
