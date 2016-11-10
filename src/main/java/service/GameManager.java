package service;

import domaine.Age;
import domaine.Building;
import domaine.Joueur;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by jean_letard on 09/11/2016.
 */
public class GameManager {

    private final GameService gameService;

    private final List<Joueur> joueurs;

    private Age currentAge = Age.I;

    private final List<Building> defausse = new ArrayList<>();

    private final Map<Joueur, ConstructionValidator.Structure> actionsDesJoueurs = new HashMap<>();


    public GameManager(GameService gameService) {
        this.gameService = gameService;
        joueurs = new ArrayList<>();
    }


    public void startGame(List<String> joueurNames){
        if(!joueurs.isEmpty()){
            throw new RuntimeException("Game already started");
        }

        joueurNames.forEach(name -> this.joueurs.add(Joueur.builder().setName(name).build()));
        gameService.initialiserPlaces(joueurs);
        gameService.distribuerCartes(Age.I, joueurs);

    }

    public void setAction(ConstructionValidator.Structure structure) {
        actionsDesJoueurs.put(structure.joueur, structure);
    }

    public List<Joueur> getJoueursAttendus(){
        return joueurs.stream().filter(j -> !actionsDesJoueurs.keySet().contains(j)).collect(Collectors.toList());
    }

    public void playStep() {

        if(getJoueursAttendus().isEmpty()){
            // Appliquer les actions

            if(gameService.isEndOfTheAge(joueurs)){
                gameService.resoudreLesConflits(currentAge, joueurs);

                if(currentAge == Age.lastAge){
                    // TODO :  Fin de la partie
                } else {
                    //Passage age suivant
                    gameService.defausserDerniereCarte(joueurs, defausse);
                    currentAge = Age.values()[currentAge.ordinal()+1];
                    gameService.distribuerCartes(currentAge, joueurs);
                }
            }
        } else {
            // TODO en attente des joueurs...
        }
    }

    public List<Joueur> getStatus() {
        return joueurs;
    }
}
