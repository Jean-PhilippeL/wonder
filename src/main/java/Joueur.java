import java.util.ArrayList;
import java.util.List;

/**
 * Created by jean_letard on 18/10/2016.
 */
public class Joueur {

    private String name;

    private final List<Building> cartesEnMain = new ArrayList<>();
    private final List<Building> cartesConstruites = new ArrayList<>();


    public void displayNombreDePièces() {
        System.out.println("nb pièces : " + nombreDePièces);
    }

    public int getNombreDePièces() {
        return nombreDePièces;
    }

    private int nombreDePièces = 6;

    public void ajouterPieces(int nombre) {
        nombreDePièces += nombre;
    }


    public Joueur(String name) {
        this.name = name;
    }


    public void construit(Building building) {
        if(cartesEnMain.remove(building)){
            building.construit(this);
            cartesConstruites.add(building);
        }
        throw new RuntimeException("can't construct this building");
    }

    public void defausse(Building building,  List<Building> defausse) {
        if(cartesEnMain.remove(building)){
            ajouterPieces(3);
            defausse.add(building);
        }
        throw new RuntimeException("can't defausse this building");
    }

    public void construireMerveille(Building building){

    }

    public List<Resource> getProduction(){
        return cartesConstruites.stream().map(b -> b.getProduction()).reduce((resources, resources2) -> {List<Resource> list = new ArrayList<>(resources);
            list.addAll(resources2);
        return list;
        }).get();
    }

}
