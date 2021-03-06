package domaine;

import java.util.*;
import java.util.function.Consumer;

/**
 * Created by jean_letard on 18/10/2016.
 */
public class Building {


    public static final List<Building> allCards = new ArrayList<>();


    private final String name;
    private final Age age;
    private final Couleur couleur;
    private final List<Resource> coutRessources;
    private final int coutPiecesOr;
    private final List<Resource> production;
    private final List<Resource> productionChoisie;
    private final int boucliersMilitaires;
    private final int nombrePointsVictoire;
    private final SymboleScience symboleScience;
    private final Optional<Consumer<Joueur>> onConstructMethod;
    private final Optional<Building> parent;

    public String getName() {
        return name;
    }
    public Age getAge() {
        return age;
    }
    public List<Resource> getProduction() {
        return production;
    }

    public List<Resource> getProductionChoisie() {
        return productionChoisie;
    }

    public int getBoucliersMilitaires() {
        return boucliersMilitaires;
    }

    public int getCoutPiecesOr() {
        return coutPiecesOr;
    }

    public List<Resource> getCoutRessources() {
        return Collections.unmodifiableList(coutRessources);
    }

    public Couleur getCouleur() {
        return couleur;
    }


    private Building(BuildingBuilder buildingBuilder) {
        this.name = buildingBuilder.name;
        this.age = buildingBuilder.age;
        this.couleur = buildingBuilder.couleur;
        this.coutRessources = Collections.unmodifiableList(buildingBuilder.coutResources);
        this.coutPiecesOr = buildingBuilder.coutPiecesOr;
        this.production = Collections.unmodifiableList(buildingBuilder.production);
        this.productionChoisie = Collections.unmodifiableList(buildingBuilder.productionChoisie);
        this.boucliersMilitaires = buildingBuilder.boucliersMilitaires;
        this.nombrePointsVictoire = buildingBuilder.nombrePointsVictoire;
        this.symboleScience = buildingBuilder.symboleScience;
        this.onConstructMethod = Optional.ofNullable(buildingBuilder.onConstrucMethod);
        this.parent = Optional.ofNullable(buildingBuilder.parent);

        allCards.add(this);
    }

    public void construit(Joueur joueur) {
        onConstructMethod.ifPresent(c -> c.accept(joueur));
    }

    public void isDisponibleParChainage(Joueur joueur) {
        if (parent.isPresent()) {
            if (!joueur.getCartesConstruites().contains(parent.get())) {
                throw new RuntimeException("le joueur ne possède pas la carte requise pour le chainage");
            }
        } else {
            throw new RuntimeException("pas de chainage possible pour cette carte");
        }


    }

    public static BuildingBuilder builder(String name, Age age, Couleur couleur) {
        return new BuildingBuilder(name, age, couleur);
    }

    public static class BuildingBuilder {


        private BuildingBuilder(String name, Age age, Couleur couleur) {
            this.name = name;
            this.age = age;
            this.couleur = couleur;
        }

        private final Age age;
        private final Couleur couleur;
        private final String name;
        private List<Resource> coutResources = Collections.emptyList();
        private int coutPiecesOr = 0;
        private Building parent = null;

        private List<Resource> production = Collections.emptyList();
        public List<Resource> productionChoisie = Collections.emptyList();

        private int boucliersMilitaires;

        private int nombrePointsVictoire;

        private SymboleScience symboleScience;

        private Consumer<Joueur> onConstrucMethod;


        public BuildingBuilder withCoutResources(Resource... coutResources) {
            this.coutResources = Arrays.asList(coutResources);
            return this;
        }

        public BuildingBuilder withCoutChainage(Building parent) {
            this.parent = parent;
            return this;
        }

        public BuildingBuilder withCoutPiecesOr(int coutPiecesOr) {
            this.coutPiecesOr = coutPiecesOr;
            return this;
        }

        public BuildingBuilder withProduction(Resource... production) {
            this.production = Arrays.asList(production);
            return this;
        }

        public BuildingBuilder withProductionChoisie(Resource... productionChoisie) {
            this.productionChoisie = Arrays.asList(productionChoisie);
            return this;
        }


        public BuildingBuilder setBoucliersMilitaires(int boucliersMilitaires) {
            this.boucliersMilitaires = boucliersMilitaires;
            return this;
        }

        public BuildingBuilder withPointsVictoire(int nombrePointsVictoire) {
            this.nombrePointsVictoire = nombrePointsVictoire;
            return this;
        }

        public BuildingBuilder setSymboleScience(SymboleScience symboleScience) {
            this.symboleScience = symboleScience;
            return this;
        }

        public Building build() {
            return new Building(this);
        }

        public BuildingBuilder withOnConstructMethode(Consumer<Joueur> method) {
            this.onConstrucMethod = method;
            return this;
        }
    }


    public enum Couleur {
        MARRON, GRIS, BLEU, JAUNE, ROUGE, VERT, VIOLET
    }
}
