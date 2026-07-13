package com.ipi.world_cup_b3.dto.response;

public record ConnexionResponse(
        String token,
        UtilisateurResponse utilisateur
) {}