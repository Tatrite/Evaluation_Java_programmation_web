package com.ipi.world_cup_b3.dto.request;

import jakarta.validation.constraints.NotNull;

public record PronosticRequest(
        @NotNull Long matchId,
        @NotNull Integer scoreEquipeAPredit,
        @NotNull Integer scoreEquipeBPredit
) {}
