package service;

import domaine.Age;
import domaine.Building;
import domaine.Deck;
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
        joueur1 = Joueur.builder().build();
        joueur2 = Joueur.builder().build();

       joueurs = Arrays.asList(joueur1, joueur2);
    }

    @Test
    public void initialiserPlacesTest()  {
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
    public void distribuerCartesTest()   {
        //Given

        //When
        gameService.distribuerCartes(Age.I, joueurs);
        assertThat(joueur1.getCartesEnMain()).isNotEmpty();
        final int nbCartes = joueur1.getCartesEnMain().size();
        assertThat(nbCartes).isGreaterThan(0);

        final Set<Building> allCards = new HashSet<>();

        for (Joueur joueur : joueurs) {
            assertThat(joueur.getCartesEnMain()).hasSize(nbCartes);
            allCards.addAll(joueur.getCartesEnMain());
        }

        assertThat(allCards).hasSize(nbCartes * joueurs.size());
    }



    @Test
    public void resoudreLesConflitsTest(){
        //Given
        joueur1.construire(Deck.tourDeGarde);
        joueur1.setVoisinGauche(joueur2);
        joueur2.setVoisinGauche(joueur1);

        //When
        gameService.resoudreLesConflits(Age.I, joueurs);

        //Then
        assertThat(joueur1.getJetonsMilitaires()).containsExactly(Age.I.nbPointVictoireMilitaire, Age.I.nbPointVictoireMilitaire);
        assertThat(joueur2.getJetonsMilitaires()).containsExactly(Age.I.nbPointDefaiteMilitaire, Age.I.nbPointDefaiteMilitaire);
        assertThat(joueur1.getScoreMilitaire()).isEqualTo(2);
        assertThat(joueur2.getScoreMilitaire()).isEqualTo(-2);
    }
}