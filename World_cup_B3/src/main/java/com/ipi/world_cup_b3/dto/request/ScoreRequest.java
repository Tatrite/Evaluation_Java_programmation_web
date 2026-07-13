package com.ipi.world_cup_b3.dto.request;

import jakarta.validation.constraints.NotNull;

public record ScoreRequest(
        @NotNull Integer scoreEquipeA,
        @NotNull Integer scoreEquipeB
) {}
