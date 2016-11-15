package infra;

import api.CardRepresentation;
import api.MyPlayerRepresentation;
import api.PlayerRepresentation;
import domaine.Building;
import domaine.Joueur;

import java.util.stream.Collectors;

/**
 * Created by jean_letard on 15/11/2016.
 */
public class ApiAdapter {

    public MyPlayerRepresentation toMyPlayerRepresentation(Joueur joueur){
        final MyPlayerRepresentation player = new MyPlayerRepresentation();
        player.setName(joueur.getName());
        player.setVoisinGauche(joueur.getVoisinGauche().getName());
        player.setVoisinDroite(joueur.getVoisinDroite().getName());
        player.setCartesEnMain(joueur.getCartesEnMain().stream().map(this::toCardRepresentation).collect(Collectors.toList()) );
        return player;
    }

    public PlayerRepresentation toOtherPlayerRepresentation(Joueur joueur){
        final PlayerRepresentation player = new PlayerRepresentation();
        player.setName(joueur.getName());
        player.setVoisinGauche(joueur.getVoisinGauche().getName());
        player.setVoisinDroite(joueur.getVoisinDroite().getName());
        return player;
    }


    private CardRepresentation toCardRepresentation(Building building){
        final CardRepresentation cardRepresentation = new CardRepresentation();
        cardRepresentation.setName(building.getName());
        cardRepresentation.setAge(building.getAge().name());
        return cardRepresentation;
    }
}
