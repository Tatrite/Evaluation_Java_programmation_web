package com.ipi.world_cup_b3.dto.response;

import com.ipi.world_cup_b3.Entity.Pronostic;

public record PronosticResponse(
        Long id,
        MatchResponse match,
        int scoreEquipeAPredit,
        int scoreEquipeBPredit,
        int pointsObtenus
) {
    public static PronosticResponse from(Pronostic p) {
        return new PronosticResponse(
                p.getId(), MatchResponse.from(p.getMatch()),
                p.getScoreEquipeAPredit(), p.getScoreEquipeBPredit(), p.getPointsObtenus()
        );
    }
}
