package com.ipi.world_cup_b3.controller;

import com.ipi.world_cup_b3.Entity.Utilisateur;
import com.ipi.world_cup_b3.dto.request.LigueRequest;
import com.ipi.world_cup_b3.dto.request.RejoindreLigueRequest;
import com.ipi.world_cup_b3.dto.response.LigueResponse;
import com.ipi.world_cup_b3.service.LigueService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ligues")
public class LigueController {

    private final LigueService ligueService;

    public LigueController(LigueService ligueService) {
        this.ligueService = ligueService;
    }

    @PostMapping
    public ResponseEntity<LigueResponse> creerLigue(
            @Valid @RequestBody LigueRequest requete,
            @RequestAttribute("utilisateurCourant") Utilisateur joueur) {
        return ResponseEntity.status(HttpStatus.CREATED).body(ligueService.creerLigue(requete, joueur));
    }

    @PostMapping("/rejoindre")
    public ResponseEntity<LigueResponse> rejoindreLigue(
            @Valid @RequestBody RejoindreLigueRequest requete,
            @RequestAttribute("utilisateurCourant") Utilisateur joueur) {
        return ResponseEntity.ok(ligueService.rejoindreLigue(requete, joueur));
    }

    @GetMapping("/mes-ligues")
    public ResponseEntity<List<LigueResponse>> getMesLigues(
            @RequestAttribute("utilisateurCourant") Utilisateur joueur) {
        return ResponseEntity.ok(ligueService.getMesLigues(joueur));
    }
}
