package com.ipi.world_cup_b3.service;

import com.ipi.world_cup_b3.Entity.Ligue;
import com.ipi.world_cup_b3.Entity.Utilisateur;
import com.ipi.world_cup_b3.Repository.LigueRepository;
import com.ipi.world_cup_b3.Repository.UtilisateurRepository;
import com.ipi.world_cup_b3.dto.response.ClassementResponse;
import com.ipi.world_cup_b3.exception.RessourceNonTrouveeException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ClassementService {

    private final UtilisateurRepository utilisateurRepository;
    private final LigueRepository ligueRepository;

    public ClassementService(UtilisateurRepository utilisateurRepository, LigueRepository ligueRepository) {
        this.utilisateurRepository = utilisateurRepository;
        this.ligueRepository = ligueRepository;
    }

    public List<ClassementResponse> getClassementGeneral() {
        // On récupère tous les utilisateurs triés par points descendants
        List<Utilisateur> utilisateurs = utilisateurRepository.findAll(Sort.by(Sort.Direction.DESC, "points"));

        return attribuerPositions(utilisateurs);
    }

    public List<ClassementResponse> getClassementLigue(Long ligueId) {
        Ligue ligue = ligueRepository.findById(ligueId)
                .orElseThrow(() -> new RessourceNonTrouveeException("Ligue non trouvée"));

        // On extrait les membres, puis on les trie manuellement par points descendants
        List<Utilisateur> membresTries = ligue.getMembres().stream()
                .sorted((u1, u2) -> Integer.compare(u2.getPoints(), u1.getPoints()))
                .toList();

        return attribuerPositions(membresTries);
    }

    // Petite méthode utilitaire pour générer l'index 1, 2, 3... du classement
    private List<ClassementResponse> attribuerPositions(List<Utilisateur> liste) {
        List<ClassementResponse> reponse = new ArrayList<>();
        for (int i = 0; i < liste.size(); i++) {
            Utilisateur u = liste.get(i);
            reponse.add(new ClassementResponse(i + 1, u.getPseudo(), u.getPoints()));
        }
        return reponse;
    }
}
