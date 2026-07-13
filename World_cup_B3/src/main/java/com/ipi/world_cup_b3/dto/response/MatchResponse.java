package com.ipi.world_cup_b3.dto.response;

import com.ipi.world_cup_b3.Entity.Match;

import java.time.LocalDateTime;

public record MatchResponse(
        Long id,
        String equipeA,
        String equipeB,
        LocalDateTime dateCoupEnvoi,
        String statut,
        Integer scoreEquipeA,
        Integer scoreEquipeB
) {
    public static MatchResponse from(Match m) {
        return new MatchResponse(
                m.getId(), m.getEquipeA(), m.getEquipeB(),
                m.getDateCoupEnvoi(), m.getStatut().name(),
                m.getScoreEquipeA(), m.getScoreEquipeB()
        );
    }
}
