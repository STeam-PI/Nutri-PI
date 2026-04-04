package com.pi.nutri.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.pi.nutri.model.Usuario;
import com.pi.nutri.repository.UsuarioRepository;

import lombok.var;

@Service
public class UsuarioService {

    UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository repository) {
        this.usuarioRepository = repository;
    }

    public Usuario salvarUsuario(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    public List<Usuario> listarUsuarios() {
        return usuarioRepository.findAll();
    }

}
