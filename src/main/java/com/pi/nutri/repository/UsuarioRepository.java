package com.pi.nutri.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pi.nutri.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByEmail(String email);
    Optional<Usuario> findByResetToken(String resetToken);
}
