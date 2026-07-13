package com.ipi.world_cup_b3.service;

import com.ipi.world_cup_b3.Entity.*;
import com.ipi.world_cup_b3.Repository.MatchRepository;
import com.ipi.world_cup_b3.Repository.PronosticRepository;
import com.ipi.world_cup_b3.dto.request.PronosticRequest;
import com.ipi.world_cup_b3.dto.response.PronosticResponse;
import com.ipi.world_cup_b3.exception.ConflitException;
import com.ipi.world_cup_b3.exception.RessourceNonTrouveeException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
@Service
public class PronosticService {

    private final PronosticRepository pronosticRepository;
    private final MatchRepository matchRepository;

    public PronosticService(PronosticRepository pronosticRepository, MatchRepository matchRepository) {
        this.pronosticRepository = pronosticRepository;
        this.matchRepository = matchRepository;
    }

    public PronosticResponse EnregistrerPari(PronosticRequest requete, Utilisateur joueur) {
        Match match = matchRepository.findById(requete.matchId())
                .orElseThrow(() -> new RessourceNonTrouveeException("Match non trouvé"));

        // Anti-triche : Vérification de l'heure du coup d'envoi
        if (LocalDateTime.now().isAfter(match.getDateCoupEnvoi())) {
            throw new ConflitException("Trop tard ! Le match a déjà commencé ou est terminé.");
        }

        // Unicité : Un seul prono par utilisateur et par match
        if (pronosticRepository.existsByUtilisateurIdAndMatchId(joueur.getId(), match.getId())) {
            throw new ConflitException("Vous avez déjà enregistré un pronostic pour ce match.");
        }

        Pronostic prono = new Pronostic();
        prono.setUtilisateur(joueur);
        prono.setMatch(match);
        prono.setScoreEquipeAPredit(requete.scoreEquipeAPredit());
        prono.setScoreEquipeBPredit(requete.scoreEquipeBPredit());

        return PronosticResponse.from(pronosticRepository.save(prono));
    }

    public List<PronosticResponse> getMesPronostics(Utilisateur joueur) {
        return pronosticRepository.findByUtilisateurId(joueur.getId())
                .stream().map(PronosticResponse::from).toList();
    }
}
