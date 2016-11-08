package service;

import domaine.Age;
import domaine.Building;
import domaine.Joueur;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by jean_letard on 07/11/2016.
 */
public class GameServiceTest {

    GameService gameService = new GameService();

    Joueur joueur1;
    Joueur joueur2;

    List<Joueur> joueurs;

    @Before
    public void setup() {
        final Joueur joueur1 = Joueur.builder().build();
        final Joueur joueur2 = Joueur.builder().build();

        final List<Joueur> joueurs = Arrays.asList(joueur1, joueur2);
    }

    @Test
    public void initialiserPlacesTest() throws Exception {
        //Given

        //When
        gameService.initialiserPlaces(joueurs);

        //Then
        final List<Joueur> voisinsDeGauche = new ArrayList<>();
        final List<Joueur> voisinsDeDroite = new ArrayList<>();
        for (Joueur joueur : joueurs) {
            voisinsDeGauche.add(joueur.getVoisinGauche());
            voisinsDeDroite.add(joueur.getVoisinDroite());
            assertThat(joueur.getVoisinGauche())
                    .isNotNull()
                    .isNotEqualTo(joueur);

            assertThat(joueur)
                    .isNotNull()
                    .isEqualTo(joueur.getVoisinGauche().getVoisinDroite());
        }

        assertThat(voisinsDeDroite).containsAll(joueurs);
        assertThat(voisinsDeGauche).containsAll(joueurs);

    }

    @Test
    public void distribuerCartesTest() throws Exception {
        //Given

        //When
        gameService.distribuerCartes(Age.I, joueurs);
        assertThat(joueur1.getCartesEnMain()).isNotEmpty();
        final int nbCartes = joueur1.getCartesEnMain().size();

        final Set<Building> allCards = new HashSet<>();

        for (Joueur joueur : joueurs) {
            assertThat(joueur.getCartesEnMain()).hasSize(nbCartes);
            allCards.addAll(joueur.getCartesEnMain());
        }

        assertThat(allCards).hasSize(nbCartes * joueurs.size());
    }
}