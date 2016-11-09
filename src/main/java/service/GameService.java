package service;

import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import domaine.Age;
import domaine.Building;
import domaine.Joueur;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static service.Jeu.allCards;
import static service.Jeu.allPlayers;

/**
 * Created by jean_letard on 07/11/2016.
 */
public class GameService {

    public void initialiserPlaces(List<Joueur> joueurs) {
        Collections.shuffle(joueurs);

        Iterables.getFirst(joueurs, null).setVoisinGauche(Iterables.getLast(joueurs));

        for (Joueur joueur : joueurs.subList(1, joueurs.size())) {
            joueur.setVoisinGauche(joueurs.get(joueurs.indexOf(joueur) - 1));
        }
    }

    public void distribuerCartes(Age age, List<Joueur> joueurs) {
        final List<Building> cartesDeAge = allCards.stream().filter(b -> b.getAge() == age).collect(Collectors.toList());

        Collections.shuffle(cartesDeAge);

        final int nombreJoueurs = joueurs.size();
        final int nbCartesParJoueur = cartesDeAge.size() / nombreJoueurs;
        final List<Building> cartesEnJeu = cartesDeAge.subList(0, nbCartesParJoueur * nombreJoueurs);

        int i = 0;
        for (List<Building> cards : Lists.partition(cartesEnJeu, nbCartesParJoueur)) {
            Joueur joueur = allPlayers.get(i++);
            cards.forEach(c -> joueur.ajouterCarteEnMain(c));
        }
    }

    public void resoudreLesConflits(Age age, List<Joueur> joueurs) {
        joueurs.forEach(joueur -> {
            resoudreLesConflits(age, joueur, joueur.getVoisinGauche());
            resoudreLesConflits(age, joueur, joueur.getVoisinDroite());
        });

    }

    private void resoudreLesConflits(Age age, Joueur joueur, Joueur voisin) {
        final int nbBoucliers = joueur.getNombreDeBoucliers();
        if (nbBoucliers < voisin.getNombreDeBoucliers()) {
            joueur.ajouterJetonMilitaire(age.nbPointDefaiteMilitaire);
        } else if (nbBoucliers > voisin.getNombreDeBoucliers()) {
            joueur.ajouterJetonMilitaire(age.nbPointVictoireMilitaire);
        }
    }

    public boolean isEndOfTheAge(List<Joueur> joueurs){
        return Iterables.getLast(joueurs).getCartesEnMain().size() == 1;
    }

    public void defausserDerniereCarte(List<Joueur> joueurs, List<Building> defausse) {
        for(Joueur joueur : joueurs){
            defausse.add(joueur.defausserFinDeTour());
        }
    }

}
