package com.pi.nutri.controller;

import com.pi.nutri.dto.UsuarioCadastroDTO;
import com.pi.nutri.dto.UsuarioResponseDTO;
import com.pi.nutri.model.Usuario;
import com.pi.nutri.service.UsuarioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping
    public ResponseEntity<UsuarioResponseDTO> criarUsuario(@RequestBody UsuarioCadastroDTO dto) {
        Usuario novoUsuario = usuarioService.salvarUsuario(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(UsuarioResponseDTO.de(novoUsuario));
    }

    @GetMapping
    public ResponseEntity<List<UsuarioResponseDTO>> listarUsuarios() {
        List<UsuarioResponseDTO> usuarios = usuarioService.listarUsuarios()
                .stream()
                .map(UsuarioResponseDTO::de)
                .toList();
        return ResponseEntity.ok(usuarios);
    }
}
