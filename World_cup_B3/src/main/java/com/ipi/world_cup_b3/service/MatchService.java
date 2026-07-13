package com.ipi.world_cup_b3.service;

import com.ipi.world_cup_b3.Entity.*;
import com.ipi.world_cup_b3.Repository.MatchRepository;
import com.ipi.world_cup_b3.Repository.PronosticRepository;
import com.ipi.world_cup_b3.dto.request.ScoreRequest;
import com.ipi.world_cup_b3.dto.response.MatchResponse;
import com.ipi.world_cup_b3.Repository.UtilisateurRepository;
import com.ipi.world_cup_b3.exception.AccesRefuseException;
import com.ipi.world_cup_b3.exception.ConflitException;
import com.ipi.world_cup_b3.exception.RessourceNonTrouveeException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MatchService {

    private final MatchRepository matchRepository;
    private final PronosticRepository pronosticRepository;
    private final UtilisateurRepository utilisateurRepository;

    public MatchService(MatchRepository matchRepository, PronosticRepository pronosticRepository, UtilisateurRepository utilisateurRepository) {
        this.matchRepository = matchRepository;
        this.pronosticRepository = pronosticRepository;
        this.utilisateurRepository = utilisateurRepository;
    }

    public List<MatchResponse> listerMatchs(String statutStr) {
        List<Match> matchs;
        if (statutStr != null && !statutStr.isBlank()) {
            StatutMatch statut = StatutMatch.valueOf(statutStr.toUpperCase());
            matchs = matchRepository.findByStatut(statut);
        } else {
            matchs = matchRepository.findAll();
        }
        return matchs.stream().map(MatchResponse::from).toList();
    }

    @Transactional
    public MatchResponse validerScore(Long matchId, ScoreRequest requete, Utilisateur admin) {
        // Vérification du rôle Admin
        if (admin.getRole() != Role.ADMIN) {
            throw new AccesRefuseException("Seul l'arbitre (ADMIN) peut valider un score !");
        }

        Match match = matchRepository.findById(matchId)
                .orElseThrow(() -> new RessourceNonTrouveeException("Match non trouvé"));

        if (match.getStatut() == StatutMatch.TERMINE) {
            throw new ConflitException("Le score de ce match a déjà été validé.");
        }

        // Enregistrement du score final
        match.setScoreEquipeA(requete.scoreEquipeA());
        match.setScoreEquipeB(requete.scoreEquipeB());
        match.setStatut(StatutMatch.TERMINE);
        matchRepository.save(match);

        // Moteur de points : calcul pour chaque pronostic
        List<Pronostic> pronostics = pronosticRepository.findByMatchId(matchId);
        for (Pronostic prono : pronostics) {
            int pointsGagnes = calculerPoints(
                    match.getScoreEquipeA(), match.getScoreEquipeB(),
                    prono.getScoreEquipeAPredit(), prono.getScoreEquipeBPredit()
            );

            prono.setPointsObtenus(pointsGagnes);
            pronosticRepository.save(prono);

            // Attribution des points au supporter
            Utilisateur supporter = prono.getUtilisateur();
            supporter.setPoints(supporter.getPoints() + pointsGagnes);
            utilisateurRepository.save(supporter);
        }

        return MatchResponse.from(match);
    }

    private int calculerPoints(int scoreA, int scoreB, int pronoA, int pronoB) {
        // 1. Vérification du score exact (100 pts issue + 50 pts bonus = 150 pts)
        if (scoreA == pronoA && scoreB == pronoB) {
            return 150;
        }

        // 2. Vérification de la bonne issue (100 pts)
        boolean bonResultat = (scoreA > scoreB && pronoA > pronoB) ||  // Victoire A
                (scoreA < scoreB && pronoA < pronoB) ||  // Victoire B
                (scoreA == scoreB && pronoA == pronoB);  // Nul

        return bonResultat ? 100 : 0;
    }
}
