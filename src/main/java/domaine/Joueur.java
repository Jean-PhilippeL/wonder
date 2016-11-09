package domaine;

import com.google.common.collect.Iterables;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by jean_letard on 18/10/2016.
 */
public class Joueur {

    public static final int NB_PIECES_INITIAL = 6;

    private final String name;
    private final List<Building> cartesEnMain;
    private final List<Building> cartesConstruites;
    private final List<Integer> jetonsMilitaires;
    private int nombreDePièces;

    private Joueur voisinGauche;
    private Joueur voisinDroite;

    public static JoueurBuilder builder() {
        return new JoueurBuilder();
    }


    public List<Building> getCartesEnMain() {
        return Collections.unmodifiableList(cartesEnMain);
    }

    public List<Building> getCartesConstruites() {
        return Collections.unmodifiableList(cartesConstruites);
    }

    public List<Integer> getJetonsMilitaires() {
        return Collections.unmodifiableList(jetonsMilitaires);
    }

    public int getScoreMilitaire() {
        return jetonsMilitaires.stream().reduce((j1, j2) -> j1 + j2).orElse(0);
    }

    public void ajouterCarteEnMain(Building building) {
        cartesEnMain.add(building);
    }

    public void ajouterJetonMilitaire(Integer jeton) {
        jetonsMilitaires.add(jeton);
    }

    public void displayNombreDePièces() {
        System.out.println("nb pièces : " + nombreDePièces);
    }

    public int getNombreDePièces() {
        return nombreDePièces;
    }

    public int getNombreDeBoucliers() {
        return cartesConstruites.stream().map(c -> c.getBoucliersMilitaires()).reduce((b1, b2) -> b1 + b2).orElse(0);
    }


    public void ajouterPieces(int nombre) {
        nombreDePièces += nombre;
    }


    private Joueur(JoueurBuilder builder) {
        this.name = builder.name;
        this.cartesEnMain = new ArrayList<>(builder.cartesEnMain);
        this.cartesConstruites = new ArrayList<>(builder.cartesConstruites);
        this.jetonsMilitaires = new ArrayList<>(builder.jetonsMilitaires);
        this.nombreDePièces = builder.nombreDePièces;
    }


    public void construire(Building building) {
        //if (cartesEnMain.remove(building)) { //Todo : à vérifier en amont
        final int coutConstruction = building.getCoutPiecesOr();
        if (coutConstruction > nombreDePièces) {
            throw new RuntimeException("Pas assez d'argent pour construire"); //Déja vérifié avant
        }
        nombreDePièces -= coutConstruction;
        building.construit(this);
        cartesConstruites.add(building);
        //} else {
        //  throw new RuntimeException("can't construct this building");
        //}
    }


    public void construireParChainage(Building building) {
        if (cartesEnMain.remove(building)) {
            building.isDisponibleParChainage(this); //Déja vérifié avant
            cartesConstruites.add(building);
        } else {
            throw new RuntimeException("can't construct this building");
        }
    }

    public void defausser(Building building, List<Building> defausse) {
        if (cartesEnMain.remove(building)) {
            ajouterPieces(3);
            defausse.add(building);
        } else {
            throw new RuntimeException("can't defausser this building");
        }
    }

    public Building defausserFinDeTour() {
        if (cartesEnMain.size() != 1) {
            throw new RuntimeException("il ne devrait y avoir qu'une seule carte à cette étape");
        }
        Building lastCard = Iterables.getLast(cartesEnMain);
        cartesEnMain.remove(lastCard);
        return lastCard;
    }

    public void construireMerveille(Building building) {

    }

    public Joueur getVoisinGauche() {
        return voisinGauche;
    }

    public void setVoisinGauche(Joueur voisinGauche) {
        this.voisinGauche = voisinGauche;
        voisinGauche.voisinDroite = this;
    }

    public Joueur getVoisinDroite() {
        return voisinDroite;
    }

    public static class JoueurBuilder {

        private String name;
        private List<Building> cartesEnMain = Collections.emptyList();
        private List<Building> cartesConstruites = Collections.emptyList();
        private List<Integer> jetonsMilitaires = Collections.emptyList();
        private int nombreDePièces = NB_PIECES_INITIAL;

        private JoueurBuilder() {

        }

        public Joueur build() {
            return new Joueur(this);
        }


        public JoueurBuilder setName(String name) {
            this.name = name;
            return this;
        }

        public JoueurBuilder setCartesEnMain(List<Building> cartesEnMain) {
            this.cartesEnMain = cartesEnMain;
            return this;
        }

        public JoueurBuilder setCartesConstruites(List<Building> cartesConstruites) {
            this.cartesConstruites = cartesConstruites;
            return this;
        }

        public JoueurBuilder withJetonsMilitaires(List<Integer> jetonsMilitaires) {
            this.jetonsMilitaires = jetonsMilitaires;
            return this;
        }

        public JoueurBuilder setNombreDePièces(int nombreDePièces) {
            this.nombreDePièces = nombreDePièces;
            return this;
        }
    }

}
