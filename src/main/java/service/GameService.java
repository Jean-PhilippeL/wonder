package service;

import com.google.common.collect.Iterables;
import domaine.Joueur;

import java.util.Collections;
import java.util.List;

/**
 * Created by jean_letard on 07/11/2016.
 */
public class GameService {

    public void initialiserPlaces(List<Joueur> joueurs){
        Collections.shuffle(joueurs);

        Iterables.getFirst(joueurs, null).setVoisinGauche(Iterables.getLast(joueurs));

        for(Joueur joueur : joueurs.subList(1, joueurs.size()) ){
            joueur.setVoisinGauche(joueurs.get(joueurs.indexOf(joueur)-1));
        }
    }
}
