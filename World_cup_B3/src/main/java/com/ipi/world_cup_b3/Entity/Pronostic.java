package com.ipi.world_cup_b3.Entity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter @Setter @NoArgsConstructor
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"utilisateur_id", "match_id"}))
public class Pronostic {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "utilisateur_id")
    private Utilisateur utilisateur;

    @ManyToOne(optional = false)
    @JoinColumn(name = "match_id")
    private Match match;

    @Column(nullable = false)
    private int scoreEquipeAPredit;

    @Column(nullable = false)
    private int scoreEquipeBPredit;

    private int pointsObtenus = 0; // calculé uniquement après validation du match
}
