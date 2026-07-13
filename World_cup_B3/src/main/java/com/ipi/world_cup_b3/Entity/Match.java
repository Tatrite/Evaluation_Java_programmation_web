package com.ipi.world_cup_b3.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;


@Entity
@Getter @Setter @NoArgsConstructor
@Table(name = "rencontres")
public class Match {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String equipeA;

    @Column(nullable = false)
    private String equipeB;

    @Column(nullable = false)
    private LocalDateTime dateCoupEnvoi;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatutMatch statut = StatutMatch.A_VENIR;

    private Integer scoreEquipeA; // null tant que le match n'est pas terminé
    private Integer scoreEquipeB;
}
