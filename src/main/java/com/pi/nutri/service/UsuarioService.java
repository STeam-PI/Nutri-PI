package com.pi.nutri.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.pi.nutri.dto.Login.LoginRequestDto;
import com.pi.nutri.dto.Usuario.UsuarioRequestDto;
import com.pi.nutri.dto.Usuario.UsuarioResponseDto;
import com.pi.nutri.model.Usuario;
import com.pi.nutri.repository.UsuarioRepository;
import com.pi.nutri.service.Token.TokenService;

import lombok.var;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder bCryptSecurity;
    private final TokenService tokenService;
    private final JavaMailSender mailSender;

    public UsuarioService(UsuarioRepository repository, PasswordEncoder bCryptSecurity, TokenService tokenService,
            JavaMailSender mailSender) {
        this.usuarioRepository = repository;
        this.bCryptSecurity = bCryptSecurity;
        this.tokenService = tokenService;
        this.mailSender = mailSender;
    }

    public UsuarioResponseDto salvarUsuario(UsuarioRequestDto usuarioRequest) {
        if (!usuarioRequest.getSenha().equals(usuarioRequest.getConfirmeSenha())) {
            throw new RuntimeException("As senhas não coincidem");
        }
        if (usuarioRepository.findByEmail(usuarioRequest.getEmail()).isPresent()) {
            throw new RuntimeException("Este e-mail já está cadastrado no sistema.");
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

    public String fazerLogin(LoginRequestDto login) {
        Usuario usuario = usuarioRepository.findByEmail(login.getEmail())
                .orElseThrow(() -> new RuntimeException("Usuário ou senha incorretos"));
        if (bCryptSecurity.matches(login.getSenha(), usuario.getSenha()))
            return tokenService.gerarToken(usuario);
        throw new RuntimeException("Usuário ou senha incorretos");

    }

    public void recuperacaoDeSenha(String email) {
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        String token = UUID.randomUUID().toString();
        usuario.setResetToken(token);
        usuario.setExpiracaoToken(LocalDateTime.now().plusMinutes(30));

        usuarioRepository.save(usuario);

        SimpleMailMessage mensagem = new SimpleMailMessage();
        mensagem.setTo(usuario.getEmail());
        mensagem.setSubject("NutriPi - Recuperação de senha");
        mensagem.setText("Olá " + usuario.getNome() + "!\n\n" +
                "Você solicitou a recuperação de senha. Use o código abaixo para redefinir:\n\n" +
                token + "\n\n" +
                "Este código expira em 30 minutos.");
        mailSender.send(mensagem);
    }

    public void redefinirSenha(String token, String novaSenha, String confirmeSenha) {
        Usuario usuario = usuarioRepository.findByResetToken(token)
                .orElseThrow(() -> new RuntimeException("Token inválido"));
        if (usuario.getExpiracaoToken().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("Seu token expirou");
        }
        if(!novaSenha.equals(confirmeSenha)){
            throw new RuntimeException("As senhas não coincidem");
        }

        usuario.setSenha(bCryptSecurity.encode(novaSenha));
        usuario.setExpiracaoToken(null);
        usuario.setResetToken(null);
        usuarioRepository.save(usuario);
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
