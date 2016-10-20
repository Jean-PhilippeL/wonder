import org.assertj.core.api.Assertions;
import org.junit.Test;

/**
 * Created by jean_letard on 19/10/2016.
 */
public class JoueurTest {

    Joueur joueur = new Joueur("nom");


    @Test
    public void displayNombreDePièces() throws Exception {

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
    public void construit() throws Exception {


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