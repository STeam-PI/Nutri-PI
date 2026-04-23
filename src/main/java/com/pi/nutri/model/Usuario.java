package com.pi.nutri.model;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.Data;
@Data
@Entity
@Table(name = "usuarios")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY )
    private Long id;

    @Column(nullable = false, length = 100)
    private String nome;
    
    @Column(nullable = false, length = 100)
    private String email;

    @Column(nullable = false)
    private String senha;

    @Column(name = "is_nutri", nullable = false)
    private boolean isNutri;

    @Column(length = 20)
    private String crn;

    @Column(name = "reset_token")
    private String resetToken;

    @Column(name = "expiracao_token")
    private LocalDateTime expiracaoToken;
}
