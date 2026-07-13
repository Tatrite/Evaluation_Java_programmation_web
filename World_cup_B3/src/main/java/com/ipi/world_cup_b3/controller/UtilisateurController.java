package com.ipi.world_cup_b3.controller;

import com.ipi.world_cup_b3.dto.request.ConnexionRequest;
import com.ipi.world_cup_b3.dto.request.InscriptionRequest;
import com.ipi.world_cup_b3.dto.response.ConnexionResponse;
import com.ipi.world_cup_b3.dto.response.UtilisateurResponse;
import com.ipi.world_cup_b3.service.UtilisateurService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/utilisateurs")
public class UtilisateurController {

    private final UtilisateurService utilisateurService;

    public UtilisateurController(UtilisateurService utilisateurService) {
        this.utilisateurService = utilisateurService;
    }

    @PostMapping("/inscription")
    public ResponseEntity<UtilisateurResponse> inscription(@Valid @RequestBody InscriptionRequest requete) {
        return ResponseEntity.status(HttpStatus.CREATED).body(utilisateurService.inscrire(requete));
    }

    @PostMapping("/connexion")
    public ResponseEntity<ConnexionResponse> connexion(@Valid @RequestBody ConnexionRequest requete) {
        return ResponseEntity.ok(utilisateurService.connecter(requete));
    }
}