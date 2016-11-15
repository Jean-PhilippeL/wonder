package service;

import domaine.Joueur;

import java.util.ArrayList;
import java.util.List;

public class Jeu {


    public static final List<Joueur> allPlayers = new ArrayList<>();

    public static void main(String[] args) {


        Joueur joueur1 = Joueur.builder().build();
        Joueur joueur2 = Joueur.builder().build();
        Joueur joueur3 = Joueur.builder().build();

        allPlayers.add(joueur1);
        allPlayers.add(joueur2);
        allPlayers.add(joueur3);



        joueur3.getCartesEnMain().forEach(c -> System.out.println(c.getName()));

    }




}
