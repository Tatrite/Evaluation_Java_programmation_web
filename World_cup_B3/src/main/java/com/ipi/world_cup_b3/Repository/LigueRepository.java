package com.ipi.world_cup_b3.Repository;

import com.ipi.world_cup_b3.Entity.Ligue;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LigueRepository extends JpaRepository<Ligue, Long> {
    Optional<Ligue> findByCode(String code);
}

