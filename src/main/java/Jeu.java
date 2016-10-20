import java.util.ArrayList;
import java.util.List;

/**
 * Created by jean_letard on 18/10/2016.
 */
public class Jeu {

    public static void main(String[] args) {


        List<Building> defausse = new ArrayList<>();

        Joueur jp = new Joueur("JP");

        jp.displayNombreDePièces();

        jp.construit(Deck.taverne);

        jp.displayNombreDePièces();

        System.out.println("res : " + jp.getProduction() );

        jp.construit(Deck.filonDor);
        System.out.println("res : " + jp.getProduction() );
        //jp.getProduction().forEach(p -> System.out.println("res : " + p ));


    }


}
