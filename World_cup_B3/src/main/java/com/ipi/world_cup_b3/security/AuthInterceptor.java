package com.ipi.world_cup_b3.security;

import com.ipi.world_cup_b3.Entity.Utilisateur;
import com.ipi.world_cup_b3.Repository.UtilisateurRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.io.IOException;
import java.util.Optional;

@Component
public class AuthInterceptor implements HandlerInterceptor {

    private final UtilisateurRepository utilisateurRepository;

    public AuthInterceptor(UtilisateurRepository utilisateurRepository) {
        this.utilisateurRepository = utilisateurRepository;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
        String token = request.getHeader("X-Auth-Token");

        if (token == null || token.isBlank()) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token manquant");
            return false;
        }

        Optional<Utilisateur> utilisateur = utilisateurRepository.findByToken(token);
        if (utilisateur.isEmpty()) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token invalide");
            return false;
        }

        request.setAttribute("utilisateurCourant", utilisateur.get());
        return true;
    }
}
