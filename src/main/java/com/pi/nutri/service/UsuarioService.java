package com.pi.nutri.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.pi.nutri.dto.Usuario.UsuarioRequestDto;
import com.pi.nutri.dto.Usuario.UsuarioResponseDto;
import com.pi.nutri.model.Usuario;
import com.pi.nutri.repository.UsuarioRepository;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder bCryptSecurity;

    public UsuarioService(UsuarioRepository repository, PasswordEncoder bCryptSecurity) {
        this.usuarioRepository = repository;
        this.bCryptSecurity = bCryptSecurity;
    }

    public UsuarioResponseDto salvarUsuario(UsuarioRequestDto usuarioRequest) {
        if(!usuarioRequest.getSenha().equals(usuarioRequest.getConfirmeSenha())){
            throw new RuntimeException("As senhas não coincidem");
        }
        Usuario usuario = new Usuario();
        usuario.setNome(usuarioRequest.getNome());
        usuario.setEmail(usuarioRequest.getEmail());
        usuario.setSenha(bCryptSecurity.encode(usuarioRequest.getSenha()));
        usuario.setNutri(usuarioRequest.isNutri());
        usuario.setCrn(usuarioRequest.getCrn());

        Usuario usuarioSalvo = usuarioRepository.save(usuario);
        return converterUsuarioDto(usuarioSalvo);
    }

    public List<UsuarioResponseDto> listarUsuarios() {
        return usuarioRepository.findAll().stream().map(this::converterUsuarioDto).collect(Collectors.toList());
    }

    public UsuarioResponseDto converterUsuarioDto(Usuario usuario) {
        UsuarioResponseDto usuarioResponse = new UsuarioResponseDto();
        usuarioResponse.setId(usuario.getId());
        usuarioResponse.setNome(usuario.getNome());
        usuarioResponse.setEmail(usuario.getEmail());
        usuarioResponse.setNutri(usuario.isNutri());
        usuarioResponse.setCrn(usuario.getCrn());
        return usuarioResponse;
    }
}
