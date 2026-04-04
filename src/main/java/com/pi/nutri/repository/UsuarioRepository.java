package com.pi.nutri.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pi.nutri.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    
}
