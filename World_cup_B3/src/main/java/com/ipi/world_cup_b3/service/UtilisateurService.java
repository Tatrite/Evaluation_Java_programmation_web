package com.ipi.world_cup_b3.service;

import com.ipi.world_cup_b3.Entity.Role;
import com.ipi.world_cup_b3.Entity.Utilisateur;
import com.ipi.world_cup_b3.Repository.UtilisateurRepository;
import com.ipi.world_cup_b3.dto.request.ConnexionRequest;
import com.ipi.world_cup_b3.dto.request.InscriptionRequest;
import com.ipi.world_cup_b3.dto.response.ConnexionResponse;
import com.ipi.world_cup_b3.dto.response.UtilisateurResponse;
import com.ipi.world_cup_b3.exception.AccesRefuseException;
import com.ipi.world_cup_b3.exception.ConflitException;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UtilisateurService {

    private final UtilisateurRepository utilisateurRepository;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public UtilisateurService(UtilisateurRepository utilisateurRepository) {
        this.utilisateurRepository = utilisateurRepository;
    }

    public UtilisateurResponse inscrire(InscriptionRequest requete) {
        if (utilisateurRepository.findByPseudo(requete.pseudo()).isPresent()) {
            throw new ConflitException("Ce pseudo est déjà utilisé");
        }

        Utilisateur utilisateur = new Utilisateur();
        utilisateur.setPseudo(requete.pseudo());
        utilisateur.setMotDePasse(passwordEncoder.encode(requete.motDePasse()));
        utilisateur.setRole(Role.JOUEUR);

        Utilisateur enregistre = utilisateurRepository.save(utilisateur);
        return UtilisateurResponse.from(enregistre);
    }

    public ConnexionResponse connecter(ConnexionRequest requete) {
        Utilisateur utilisateur = utilisateurRepository.findByPseudo(requete.pseudo())
                .orElseThrow(() -> new AccesRefuseException("Identifiants incorrects"));

        if (!passwordEncoder.matches(requete.motDePasse(), utilisateur.getMotDePasse())) {
            throw new AccesRefuseException("Identifiants incorrects");
        }

        utilisateur.setToken(UUID.randomUUID().toString());
        utilisateurRepository.save(utilisateur);

        return new ConnexionResponse(utilisateur.getToken(), UtilisateurResponse.from(utilisateur));
    }
}
