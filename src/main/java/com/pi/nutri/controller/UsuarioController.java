package com.pi.nutri.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pi.nutri.dto.Login.EsqueciSenhaDto;
import com.pi.nutri.dto.Login.LoginRequestDto;
import com.pi.nutri.dto.Login.RedefinirSenhaDto;
import com.pi.nutri.dto.Usuario.UsuarioRequestDto;
import com.pi.nutri.dto.Usuario.UsuarioResponseDto;
import com.pi.nutri.model.Usuario;
import com.pi.nutri.service.UsuarioService;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {
    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping
    public ResponseEntity<?> criarUsuario(@RequestBody UsuarioRequestDto usuario) {
        try {
            UsuarioResponseDto novoUsuario = usuarioService.salvarUsuario(usuario);
            return ResponseEntity.status(HttpStatus.CREATED).body(novoUsuario);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PreAuthorize("hasRole('NUTRI')")
    @GetMapping
    public ResponseEntity<List<UsuarioResponseDto>> listarUsuarios() {
        List<UsuarioResponseDto> usuarios = usuarioService.listarUsuarios();
        return ResponseEntity.ok(usuarios);
    }
    @PostMapping("/esqueci-senha")
    public ResponseEntity<String> esqueciSenha(@RequestBody EsqueciSenhaDto esqueciSenha) {
        try {
            usuarioService.recuperacaoDeSenha(esqueciSenha.getEmail());
            return ResponseEntity.ok("Foi enviado um link de recuperação para o seu e-mail");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping("/reset-senha")
    public ResponseEntity<String> recuperarSenha(@RequestBody RedefinirSenhaDto redefinirSenhaDto) {
        try {
            usuarioService.redefinirSenha(redefinirSenhaDto.getToken(), redefinirSenhaDto.getNovaSenha());
            return ResponseEntity.ok("Sua senha foi redefinida");

        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequestDto login) {
        try {
            String token = usuarioService.fazerLogin(login);
            return ResponseEntity.ok(token);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }
}
