package com.ipi.world_cup_b3.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record InscriptionRequest(
        @NotBlank @Size(min = 3, max = 30) String pseudo,
        @NotBlank @Size(min = 8) String motDePasse
) {}