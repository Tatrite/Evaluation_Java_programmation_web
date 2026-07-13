package com.ipi.world_cup_b3.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record LigueRequest(
        @NotBlank @Size(min = 3, max = 50) String nom
) {}
