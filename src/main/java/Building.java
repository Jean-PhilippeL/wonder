import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

/**
 * Created by jean_letard on 18/10/2016.
 */
public class Building {

    private final Age age;

    private final Couleur couleur;

    private final List<Resource> cout;

    private final List<Resource> production;

    private final Etat etat;

    private final int boucliersMilitaires;

    private final int nombrePointsVictoire;

    private final SymboleScience symboleScience;

    private final Optional<Consumer<Joueur>> onConstructMethod;

    public List<Resource> getProduction(){
        return production;
    }

    public Building(BuildingBuilder buildingBuilder) {
        this.age = buildingBuilder.age;
        this.couleur = buildingBuilder.couleur;
        this.cout = buildingBuilder.cout;
        this.production = buildingBuilder.production;
        this.etat = buildingBuilder.etat;
        this.boucliersMilitaires = buildingBuilder.boucliersMilitaires;
        this.nombrePointsVictoire = buildingBuilder.nombrePointsVictoire;
        this.symboleScience = buildingBuilder.symboleScience;
        this.onConstructMethod = Optional.ofNullable(buildingBuilder.onConstrucMethod);
    }

    public void construit(Joueur joueur){
        onConstructMethod.ifPresent(c -> c.accept(joueur));
    }

    public static BuildingBuilder builder(Age age, Couleur couleur) {
        return new BuildingBuilder(age, couleur);
    }

    public static class BuildingBuilder {

        private BuildingBuilder(Age age, Couleur couleur) {
            this.age = age;
            this.couleur = couleur;
        }

        private final Age age;
        private final Couleur couleur;
        private List<Resource> cout;

        private List<Resource> production = Collections.emptyList();

        private Joueur proprietaire;

        private Etat etat;


        private int boucliersMilitaires;

        private int nombrePointsVictoire;

        private SymboleScience symboleScience;

        private Consumer<Joueur> onConstrucMethod;


        public BuildingBuilder setCout(Resource... cout) {
            this.cout = Arrays.asList(cout);
            return this;
        }

        public BuildingBuilder withProduction(Resource... production) {
            this.production = Arrays.asList(production);
            return this;
        }


        public BuildingBuilder setEtat(Etat etat) {
            this.etat = etat;
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


    public enum  Couleur {
        MARRON,GRIS,BLEU,JAUNE,ROUGE,VERT,VIOLET
    }
}
