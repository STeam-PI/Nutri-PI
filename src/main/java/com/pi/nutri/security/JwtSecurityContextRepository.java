package com.pi.nutri.security;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.context.HttpRequestResponseHolder;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.stereotype.Component;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
@SuppressWarnings("deprecation")
public class JwtSecurityContextRepository implements SecurityContextRepository {

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    public JwtSecurityContextRepository(JwtService jwtService, UserDetailsService userDetailsService) {
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
    }

    @Override
    public SecurityContext loadContext(HttpRequestResponseHolder holder) {
        return load(holder.getRequest());
    }

    @Override
    public void saveContext(SecurityContext context, HttpServletRequest request, HttpServletResponse response) {
    }

    @Override
    public boolean containsContext(HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        return header != null && header.startsWith("Bearer ");
    }

    private SecurityContext load(HttpServletRequest request) {
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        String header = request.getHeader("Authorization");

        if (header == null || !header.startsWith("Bearer ")) {
            return context;
        }

        String token = header.substring(7);
        try {
            String email = jwtService.extractUsername(token);
            if (email != null) {
                UserDetails userDetails = userDetailsService.loadUserByUsername(email);
                if (jwtService.isTokenValid(token, userDetails)) {
                    context.setAuthentication(new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities()));
                }
            }
        } catch (Exception ignored) {
        }

        return context;
    }
}
