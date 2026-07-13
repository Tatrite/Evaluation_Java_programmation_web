package com.ipi.world_cup_b3.Repository;


import com.ipi.world_cup_b3.Entity.Pronostic;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PronosticRepository extends JpaRepository<Pronostic, Long> {
    List<Pronostic> findByUtilisateurId(Long utilisateurId);
    List<Pronostic> findByMatchId(Long matchId);
    boolean existsByUtilisateurIdAndMatchId(Long utilisateurId, Long matchId);
}