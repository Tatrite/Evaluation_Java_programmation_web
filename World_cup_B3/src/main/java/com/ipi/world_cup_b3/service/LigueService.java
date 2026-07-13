package com.ipi.world_cup_b3.service;

import com.ipi.world_cup_b3.Entity.Ligue;
import com.ipi.world_cup_b3.Entity.Utilisateur;
import com.ipi.world_cup_b3.Repository.LigueRepository;
import com.ipi.world_cup_b3.Repository.UtilisateurRepository;
import com.ipi.world_cup_b3.dto.request.LigueRequest;
import com.ipi.world_cup_b3.dto.request.RejoindreLigueRequest;
import com.ipi.world_cup_b3.dto.response.LigueResponse;
import com.ipi.world_cup_b3.exception.ConflitException;
import com.ipi.world_cup_b3.exception.RessourceNonTrouveeException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class LigueService {

    private final LigueRepository ligueRepository;
    private final UtilisateurRepository utilisateurRepository;

    public LigueService(LigueRepository ligueRepository, UtilisateurRepository utilisateurRepository) {
        this.ligueRepository = ligueRepository;
        this.utilisateurRepository = utilisateurRepository;
    }

    @Transactional
    public LigueResponse creerLigue(LigueRequest requete, Utilisateur createur) {
        Ligue ligue = new Ligue();
        ligue.setNom(requete.nom());
        // Génération d'un code VIP unique de 6 caractères
        ligue.setCode(UUID.randomUUID().toString().substring(0, 6).toUpperCase());

        Ligue ligueEnregistree = ligueRepository.save(ligue);

        // Le créateur rejoint automatiquement sa propre ligue
        createur.getLigues().add(ligueEnregistree);
        utilisateurRepository.save(createur);

        return LigueResponse.from(ligueEnregistree);
    }

    @Transactional
    public LigueResponse rejoindreLigue(RejoindreLigueRequest requete, Utilisateur joueur) {
        Ligue ligue = ligueRepository.findByCode(requete.code().toUpperCase())
                .orElseThrow(() -> new RessourceNonTrouveeException("Ligue introuvable avec ce code secret."));

        if (joueur.getLigues().contains(ligue)) {
            throw new ConflitException("Vous faites déjà partie de cette ligue !");
        }

        joueur.getLigues().add(ligue);
        utilisateurRepository.save(joueur);

        return LigueResponse.from(ligue);
    }

    public List<LigueResponse> getMesLigues(Utilisateur joueur) {
        return joueur.getLigues().stream()
                .map(LigueResponse::from)
                .toList();
    }
}
