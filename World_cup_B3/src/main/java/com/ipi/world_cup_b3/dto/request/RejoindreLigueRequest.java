package com.ipi.world_cup_b3.dto.request;

import jakarta.validation.constraints.NotBlank;

public record RejoindreLigueRequest (
        @NotBlank String code
){}
