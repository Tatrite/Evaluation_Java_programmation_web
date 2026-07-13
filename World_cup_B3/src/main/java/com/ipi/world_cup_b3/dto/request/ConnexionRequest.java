package com.ipi.world_cup_b3.dto.request;

import jakarta.validation.constraints.NotBlank;

public record ConnexionRequest(
        @NotBlank String pseudo,
        @NotBlank String motDePasse
) {}
