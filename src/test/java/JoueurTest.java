import org.assertj.core.api.Assertions;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by jean_letard on 19/10/2016.
 */
public class JoueurTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    Joueur joueur = Joueur.builder().setName("jp").build();


    @Test
    public void displayNombreDePièces() throws Exception {
        // Nombre de pièces à l'origine
        Assertions.assertThat(joueur.getNombreDePièces()).isEqualTo(6);

        // Nombre de pièces après ajout
        joueur.ajouterPieces(2);

        //Then
        Assertions.assertThat(joueur.getNombreDePièces()).isEqualTo(8);
    }

    @Test
    public void ajouterPieces() throws Exception {
        // Given
        Assertions.assertThat(joueur.getNombreDePièces()).isEqualTo(6);

        // When
        joueur.ajouterPieces(2);
        joueur.ajouterPieces(2);

        //Then
        Assertions.assertThat(joueur.getNombreDePièces()).isEqualTo(10);
    }

    @Test
    public void construit_cartePasEnMain_Erreur() throws Exception {
        expectedException.expect(RuntimeException.class);
        joueur.construire(Deck.filonDor);
    }

    @Test
    public void construire_carteGratuite_ok() throws Exception {
        //Given
        joueur.ajouterCarteEnMain(Deck.filonDor);

        //When
        joueur.construire(Deck.filonDor);

        //Then
        Assertions.assertThat(joueur.getCartesEnMain()).doesNotContain(Deck.filonDor);
        Assertions.assertThat(joueur.getCartesConstruites()).contains(Deck.filonDor);
    }

    @Test
    public void construire_cartePayanteFondSuffisants_ok() throws Exception {
        //Given
        joueur.ajouterCarteEnMain(Deck.timberYard);

        //When
        joueur.construire(Deck.timberYard);

        //Then
        Assertions.assertThat(joueur.getCartesEnMain()).doesNotContain(Deck.timberYard);
        Assertions.assertThat(joueur.getCartesConstruites()).contains(Deck.timberYard);
        Assertions.assertThat(joueur.getNombreDePièces()).isEqualTo(Joueur.NB_PIECES_INITIAL - 1);
    }

    @Test
    public void construire_cartePayanteFondSuffisants_ko() throws Exception {
        expectedException.expect(RuntimeException.class);
        expectedException.expectMessage("Pas assez d'argent pour construire");

        //Given
        joueur = Joueur.builder().setNombreDePièces(0).build();
        joueur.ajouterCarteEnMain(Deck.timberYard);

        //When
        joueur.construire(Deck.timberYard);

        //Then
        Assertions.assertThat(joueur.getCartesEnMain()).doesNotContain(Deck.timberYard);
        Assertions.assertThat(joueur.getCartesConstruites()).contains(Deck.timberYard);
        Assertions.assertThat(joueur.getNombreDePièces()).isEqualTo(Joueur.NB_PIECES_INITIAL - 1);
    }

    @Test
    public void construireParChainage_joueurPossedeLaCarte_ok() throws Exception {
        //Given
        joueur = Joueur.builder()
                .setName("jp")
                .setCartesConstruites(Arrays.asList(Deck.scriptotium))
                .build();

        joueur.ajouterCarteEnMain(Deck.hotelDeVille);

        //When
        joueur.construireParChainage(Deck.hotelDeVille);

        //Then
        Assertions.assertThat(joueur.getCartesEnMain()).doesNotContain(Deck.hotelDeVille);
        Assertions.assertThat(joueur.getCartesConstruites()).hasSize(2).contains(Deck.hotelDeVille);
    }

    @Test
    public void construireParChainage_joueurNePossedeLaCarte_ko() throws Exception {
        expectedException.expect(RuntimeException.class);

        //Given
        joueur.ajouterCarteEnMain(Deck.hotelDeVille);

        //When
        joueur.construireParChainage(Deck.hotelDeVille);

        //Then : exception
    }

    @Test
    public void construireParChainage_carteSansChainage_ko() throws Exception {
        expectedException.expect(RuntimeException.class);

        //Given
        joueur.ajouterCarteEnMain(Deck.filonDor);

        //When
        joueur.construireParChainage(Deck.filonDor);

        //Then : exception
    }

    @Test
    public void defausse_carteEnMain_gagne3Pieces() throws Exception {
        //Given
        joueur.ajouterCarteEnMain(Deck.filonDor);
        List<Building> defausse = new ArrayList<>();

        //When
        joueur.defausse(Deck.filonDor, defausse);

        //Then
        Assertions.assertThat(joueur.getCartesEnMain()).isEmpty();
        Assertions.assertThat(defausse).containsExactly(Deck.filonDor);
        Assertions.assertThat(joueur.getNombreDePièces()).isEqualTo(Joueur.NB_PIECES_INITIAL + 3);

    }

    @Test
    public void joueur_nombrePiecesInitial_initialisé() throws Exception {
        Assertions.assertThat(joueur.getNombreDePièces()).isEqualTo(Joueur.NB_PIECES_INITIAL);
    }




    @Test
    public void defausse() throws Exception {

    }

    @Test
    public void construireMerveille() throws Exception {

    }

    @Test
    public void getProduction() throws Exception {

    }

}