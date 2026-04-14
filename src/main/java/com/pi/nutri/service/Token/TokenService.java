package com.pi.nutri.service.Token;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.util.Date;

import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.pi.nutri.dto.Usuario.UsuarioRequestDto;
import com.pi.nutri.model.Usuario;

@Service
public class TokenService {
    private final String secret = "Nutri_Pi_SENAC";
    public String gerarToken(Usuario usuario){
        Algorithm algorithm = Algorithm.HMAC256(secret);
        return JWT.create()
                .withIssuer("Api NutriPi") // quem emite o token
                .withSubject(usuario.getEmail()) //de quem pertence o token
                .withClaim("id", usuario.getId())
                .withClaim("nutri", usuario.isNutri())
                .withExpiresAt(gerarDataExpiracao())
                .sign(algorithm);
    }
    private Instant gerarDataExpiracao() {
return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));    }

}
