package com.ipi.world_cup_b3.controller;
import com.ipi.world_cup_b3.Entity.Utilisateur;
import com.ipi.world_cup_b3.dto.request.ScoreRequest;
import com.ipi.world_cup_b3.dto.response.MatchResponse;
import com.ipi.world_cup_b3.service.MatchService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/matchs")
public class MatchController {

    private final MatchService matchService;

    public MatchController(MatchService matchService) {
        this.matchService = matchService;
    }

    @GetMapping
    public ResponseEntity<List<MatchResponse>> getMatchs(@RequestParam(required = false) String statut) {
        return ResponseEntity.ok(matchService.listerMatchs(statut));
    }

    @PutMapping("/{id}/score")
    public ResponseEntity<MatchResponse> validerScore(
            @PathVariable Long id,
            @Valid @RequestBody ScoreRequest requete,
            @RequestAttribute("utilisateurCourant") Utilisateur admin) {
        return ResponseEntity.ok(matchService.validerScore(id, requete, admin));
    }
}
