package com.ipi.world_cup_b3.controller;

import com.ipi.world_cup_b3.Entity.Utilisateur;
import com.ipi.world_cup_b3.dto.request.PronosticRequest;
import com.ipi.world_cup_b3.dto.response.PronosticResponse;
import com.ipi.world_cup_b3.service.PronosticService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/api/pronostics")
public class PronosticController {

    private final PronosticService pronosticService;

    public PronosticController(PronosticService pronosticService) {
        this.pronosticService = pronosticService;
    }

    @PostMapping
    public ResponseEntity<PronosticResponse> enregistrerPari(
            @Valid @RequestBody PronosticRequest requete,
            @RequestAttribute("utilisateurCourant") Utilisateur joueur) {
        return ResponseEntity.status(HttpStatus.CREATED).body(pronosticService.EnregistrerPari(requete, joueur));
    }

    @GetMapping("/mes-pronostics")
    public ResponseEntity<List<PronosticResponse>> getMesPronostics(
            @RequestAttribute("utilisateurCourant") Utilisateur joueur) {
        return ResponseEntity.ok(pronosticService.getMesPronostics(joueur));
    }
}
