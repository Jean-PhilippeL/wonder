/**
 * Created by jean_letard on 19/10/2016.
 */
public interface Deck {

    Building filonDor = Building.builder(Age.I, Building.Couleur.MARRON)
            .withProduction(Resource.Minerai)
            .build();

    Building verrerie = Building.builder(Age.I, Building.Couleur.GRIS)
            .withProduction(Resource.Verre)
            .build();

    Building taverne = Building.builder(Age.I, Building.Couleur.JAUNE)
            .withOnConstructMethode(j -> j.ajouterPieces(5) )
            .build();

    Building pownshop = Building.builder(Age.I, Building.Couleur.BLEU)
            .withPointsVictoire(3)
            .build();


    Building tourDeGarde = Building.builder(Age.I, Building.Couleur.ROUGE)
            .setCout(Resource.Minerai)
            .setBoucliersMilitaires(1)
            .build();

    Building apothiquaire = Building.builder(Age.I, Building.Couleur.VERT)
            .setCout(Resource.Tissu)
            .setSymboleScience(SymboleScience.Compas)
            .build();

}
