package com.pi.nutri.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pi.nutri.model.Usuario;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Optional<Usuario> findByEmail(String email);
}
