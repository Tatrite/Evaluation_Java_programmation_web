package com.ipi.world_cup_b3.dto.response;

import com.ipi.world_cup_b3.Entity.Utilisateur;

public record UtilisateurResponse(
        Long id,
        String pseudo,
        int points
) {
    public static UtilisateurResponse from(Utilisateur u) {
        return new UtilisateurResponse(u.getId(), u.getPseudo(), u.getPoints());
    }
}
