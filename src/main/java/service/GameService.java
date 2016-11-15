package service;

import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import domaine.*;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static domaine.Building.allCards;

/**
 * Created by jean_letard on 07/11/2016.
 */
public class GameService {

    public List<Building> allCard;

    public void createCards() {
        Building filonDor = Building.builder("filonDor", Age.I, Building.Couleur.MARRON)
                .withProduction(Resource.Minerai)
                .build();

        Building timberYard = Building.builder("timberYard", Age.I, Building.Couleur.MARRON)
                .withProductionChoisie(Resource.Pierre, Resource.Bois)
                .withCoutPiecesOr(1)
                .build();

        Building verrerie = Building.builder("verrerie", Age.I, Building.Couleur.GRIS)
                .withProduction(Resource.Verre)
                .build();

        Building taverne = Building.builder("taverne", Age.I, Building.Couleur.JAUNE)
                .withOnConstructMethode(j -> j.ajouterPieces(5))
                .build();

        Building pownshop = Building.builder("pownshop", Age.I, Building.Couleur.BLEU)
                .withPointsVictoire(3)
                .build();

        Building tourDeGarde = Building.builder("tourDeGarde", Age.I, Building.Couleur.ROUGE)
                .withCoutResources(Resource.Minerai)
                .setBoucliersMilitaires(1)
                .build();

        Building apothiquaire = Building.builder("apothiquaire", Age.I, Building.Couleur.VERT)
                .withCoutResources(Resource.Tissu)
                .setSymboleScience(SymboleScience.Compas)
                .build();


        Building scriptotium = Building.builder("scriptotium", Age.I, Building.Couleur.VERT)
                .setSymboleScience(SymboleScience.PierreDeRosette)
                .withCoutResources(Resource.Papirus)
                .build();

        //domaine.Age 2
        Building hotelDeVille = Building.builder("hotelDeVille", Age.II, Building.Couleur.BLEU)
                .withCoutResources(Resource.Brique, Resource.Brique, Resource.Tissu)
                .withCoutChainage(scriptotium)
                .withPointsVictoire(4)
                .build();

        this.allCard = allCards;
    }


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
            final Joueur joueur = joueurs.get(i++);
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

    public boolean isEndOfTheAge(List<Joueur> joueurs) {
        return Iterables.getLast(joueurs).getCartesEnMain().size() == 1;
    }

    public void defausserDerniereCarte(List<Joueur> joueurs, List<Building> defausse) {
        for (Joueur joueur : joueurs) {
            defausse.add(joueur.defausserFinDeTour());
        }
    }

}
