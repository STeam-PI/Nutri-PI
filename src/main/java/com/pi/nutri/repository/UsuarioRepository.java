package com.pi.nutri.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pi.nutri.model.Usuario;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
<<<<<<< Updated upstream

=======
>>>>>>> Stashed changes
    Optional<Usuario> findByEmail(String email);
}
