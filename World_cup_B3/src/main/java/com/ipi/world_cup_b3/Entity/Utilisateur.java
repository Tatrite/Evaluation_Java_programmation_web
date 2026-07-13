package com.ipi.world_cup_b3.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Utilisateur {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String pseudo;

    @Column(nullable = false)
    private String motDePasse; // haché via BCrypt

    @Column(unique = true)
    private String token; // null tant que non connecté

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role = Role.JOUEUR;

    @Column(nullable = false)
    private int points = 0;

    @ManyToMany
    @JoinTable(
            name = "utilisateur_ligue",
            joinColumns = @JoinColumn(name = "utilisateur_id"),
            inverseJoinColumns = @JoinColumn(name = "ligue_id")
    )
    private Set<Ligue> ligues = new HashSet<>();
}