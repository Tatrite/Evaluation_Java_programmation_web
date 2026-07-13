# Evaluation_Java_programmation_web

Schéma relationnel :
```
Utilisateur (1) ---- (N) Pronostic (N) ---- (1) Match
Utilisateur (N) ---- (N) Ligue   [table de jointure utilisateur_ligue]
```

Décisions de conception :

* Points dénormalisés : je stocke un total points directement sur Utilisateur, mis à jour de façon transactionnelle quand l'admin valide un score. Alternative possible : recalculer la somme des pointsObtenus de tous les Pronostic à chaque appel de classement. Je pars sur la dénormalisation car les endpoints de classement seront probablement les plus consultés, et recalculer une somme sur toute la table Pronostic à chaque requête est inutilement coûteux. Le Pronostic garde quand même son propre pointsObtenus pour la traçabilité et pour permettre un recalcul de contrôle si besoin.
* Contrainte unique sur (utilisateur_id, match_id) dans Pronostic : un utilisateur ne doit pouvoir parier qu'une fois par match. Sans cette contrainte en base, rien n'empêche un double POST.
* Code de ligue : généré côté serveur à la création (ex. 6 caractères alphanumériques), unique en base, jamais fourni par le client.

Architecture du projet 

```
src/main/java/com/ipi/world_cup_b3/
│
├── WorldCupB3Application.java          (classe principale, déjà générée)
│
├── entity/
│   ├── Utilisateur.java
│   ├── Match.java
│   ├── Pronostic.java
│   ├── Ligue.java
│   ├── Role.java                       (enum)
│   └── StatutMatch.java                (enum)
│
├── repository/
│   ├── UtilisateurRepository.java
│   ├── MatchRepository.java
│   ├── PronosticRepository.java
│   └── LigueRepository.java
│
├── dto/
│   ├── request/
│   │   ├── InscriptionRequest.java
│   │   ├── ConnexionRequest.java
│   │   ├── PronosticRequest.java
│   │   ├── ScoreRequest.java
│   │   ├── LigueRequest.java
│   │   └── RejoindreLigueRequest.java
│   └── response/
│       ├── UtilisateurResponse.java
│       ├── MatchResponse.java
│       ├── PronosticResponse.java
│       ├── LigueResponse.java
│       └── ClassementResponse.java
│
├── service/
│   ├── UtilisateurService.java
│   ├── MatchService.java
│   ├── PronosticService.java
│   ├── LigueService.java
│   └── ClassementService.java
│
├── controller/
│   ├── UtilisateurController.java
│   ├── MatchController.java
│   ├── PronosticController.java
│   ├── LigueController.java
│   └── ClassementController.java
│
├── security/
│   ├── AuthInterceptor.java
│   └── WebConfig.java
│
└── exception/
    ├── RessourceNonTrouveeException.java
    ├── AccesRefuseException.java
    ├── ConflitException.java
    └── GlobalExceptionHandler.java     (@ControllerAdvice)
```
