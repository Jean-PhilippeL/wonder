package service;

import domaine.Joueur;
import org.assertj.core.api.Assertions;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.StrictAssertions.assertThat;

/**
 * Created by jean_letard on 07/11/2016.
 */
public class GameServiceTest {

    GameService gameService = new GameService();

    @Test
    public void name() throws Exception {
        //Given
        final Joueur joueur1 = Joueur.builder().build();
        final Joueur joueur2 = Joueur.builder().build();

        final List<Joueur> joueurs = Arrays.asList(joueur1,joueur2);

        //When
        gameService.initialiserPlaces(joueurs);

        //Then
        final List<Joueur> voisinsDeGauche = new ArrayList<>();
        final List<Joueur> voisinsDeDroite = new ArrayList<>();
        for(Joueur joueur : joueurs){
            voisinsDeGauche.add(joueur.getVoisinGauche());
            voisinsDeDroite.add(joueur.getVoisinDroite());
            assertThat(joueur.getVoisinGauche())
                    .isNotNull()
                    .isNotEqualTo(joueur);

            assertThat(joueur)
                    .isNotNull()
                   .isEqualTo(joueur.getVoisinGauche().getVoisinDroite());
        }

        Assertions.assertThat(voisinsDeDroite).containsAll(joueurs);
        Assertions.assertThat(voisinsDeGauche).containsAll(joueurs);

    }
}