package com.pi.nutri.config;

import java.io.IOException;
import java.util.Collections;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.pi.nutri.service.Token.TokenService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class SecurityFilter extends OncePerRequestFilter {
    private final TokenService tokenService;

    public SecurityFilter(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        var token = recuperarToken(request);
        if (token != null) {

            DecodedJWT jwt = tokenService.decodificarToken(token);
            String email = jwt.getSubject();
            Boolean isNutri = jwt.getClaim("isNutri").asBoolean();
            var autorizacao = isNutri ? new SimpleGrantedAuthority("ROLE_NUTRI")
                    : new SimpleGrantedAuthority("ROLE_PACIENTE");
            var autenticacao = new UsernamePasswordAuthenticationToken(email, null,
                    Collections.singletonList(autorizacao));
            SecurityContextHolder.getContext().setAuthentication(autenticacao);
        }
        filterChain.doFilter(request, response);
    }

    private String recuperarToken(HttpServletRequest request){
        var authHeader = request.getHeader("Autorizhation");
        if(authHeader == null) return null;
        return authHeader.replace("Bearer", "");
    }
}
