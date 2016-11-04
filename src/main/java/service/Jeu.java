package service;

import com.google.common.collect.Lists;
import domaine.Age;
import domaine.Building;
import domaine.Deck;
import domaine.Joueur;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class Jeu {

    public static final List<Building> allCards = new ArrayList<>();
    public static final List<Joueur> allPlayers = new ArrayList<>();

    public static void main(String[] args) {
        Deck.apothiquaire.getCouleur();



        Joueur joueur1 = Joueur.builder().build();
        Joueur joueur2 = Joueur.builder().build();
        Joueur joueur3 = Joueur.builder().build();

        allPlayers.add(joueur1);
        allPlayers.add(joueur2);
        allPlayers.add(joueur3);

        distribuerCartes(Age.I);

        joueur3.getCartesEnMain().forEach(c -> System.out.println(c.getName()));

    }

    private static void distribuerCartes(Age age) {
        List<Building> cartesDeAge = allCards.stream().filter(b -> b.getAge() == age).collect(Collectors.toList());

        Collections.shuffle(cartesDeAge);

        final int nbCartesParJoueur = cartesDeAge.size() / allPlayers.size();

        List<Building> cartesEnJeu = cartesDeAge.subList(0, nbCartesParJoueur * allPlayers.size());

       int i = 0;
        for(List<Building> cards : Lists.partition(cartesEnJeu, nbCartesParJoueur)){
            Joueur joueur = allPlayers.get(i++);
            cards.forEach(c -> joueur.ajouterCarteEnMain(c));
        }
    }


}
