package com.ipi.world_cup_b3.Repository;

import com.ipi.world_cup_b3.Entity.Match;
import com.ipi.world_cup_b3.Entity.StatutMatch;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MatchRepository extends JpaRepository<Match, Long> {
    List<Match> findByStatut(StatutMatch statut);
}