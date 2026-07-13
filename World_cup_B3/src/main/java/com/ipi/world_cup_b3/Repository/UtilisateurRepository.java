package com.ipi.world_cup_b3.Repository;

import com.ipi.world_cup_b3.Entity.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UtilisateurRepository extends JpaRepository<Utilisateur, Long> {
    Optional<Utilisateur> findByPseudo(String pseudo);
    Optional<Utilisateur> findByToken(String token);
}
