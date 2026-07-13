package com.ipi.world_cup_b3.dto.response;

import com.ipi.world_cup_b3.Entity.Ligue;

public record LigueResponse(
        Long id,
        String nom,
        String code,
        int nombreMembres
) {
    public static LigueResponse from(Ligue l) {
        return new LigueResponse(l.getId(), l.getNom(), l.getCode(), l.getMembres().size());
    }
}