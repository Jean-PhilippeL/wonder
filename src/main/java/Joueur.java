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
    private int nombreDePièces = NB_PIECES_INITIAL;

    public static JoueurBuilder builder() {
        return new JoueurBuilder();
    }


    public List<Building> getCartesEnMain() {
        return Collections.unmodifiableList(cartesEnMain);
    }

    public List<Building> getCartesConstruites() {
        return Collections.unmodifiableList(cartesConstruites);
    }

    public void ajouterCarteEnMain(Building building) {
        cartesEnMain.add(building);
    }


    public void displayNombreDePièces() {
        System.out.println("nb pièces : " + nombreDePièces);
    }

    public int getNombreDePièces() {
        return nombreDePièces;
    }


    public void ajouterPieces(int nombre) {
        nombreDePièces += nombre;
    }


    private Joueur(JoueurBuilder builder) {
        this.name = builder.name;
        this.cartesEnMain = new ArrayList<>(builder.cartesEnMain);
        this.cartesConstruites = new ArrayList<>(builder.cartesConstruites);
        this.nombreDePièces = builder.nombreDePièces;
    }


    public void construire(Building building) {
        if (cartesEnMain.remove(building)) {
            final int coutConstruction = building.getCoutPiecesOr();
            if (coutConstruction > nombreDePièces) {
                throw new RuntimeException("Pas assez d'argent pour construire");
            }
            nombreDePièces -= coutConstruction;
            building.construit(this);
            cartesConstruites.add(building);
        } else {
            throw new RuntimeException("can't construct this building");
        }
    }

    public void construireParChainage(Building building) {
        if (cartesEnMain.remove(building)) {
            building.construireParChainage(this);
            cartesConstruites.add(building);
        } else {
            throw new RuntimeException("can't construct this building");
        }
    }

    public void defausse(Building building, List<Building> defausse) {
        if (cartesEnMain.remove(building)) {
            ajouterPieces(3);
            defausse.add(building);
        } else {
            throw new RuntimeException("can't defausse this building");
        }
    }

    public void construireMerveille(Building building) {

    }

    public List<Resource> getProduction() {
        return cartesConstruites.stream().map(b -> b.getProduction()).reduce((resources, resources2) -> {
            List<Resource> list = new ArrayList<>(resources);
            list.addAll(resources2);
            return list;
        }).get();
    }

    public static class JoueurBuilder {

        private String name;
        private List<Building> cartesEnMain = Collections.emptyList();
        private List<Building> cartesConstruites = Collections.emptyList();
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

        public JoueurBuilder setNombreDePièces(int nombreDePièces) {
            this.nombreDePièces = nombreDePièces;
            return this;
        }


    }

}
