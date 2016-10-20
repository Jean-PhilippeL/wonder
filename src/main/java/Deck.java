/**
 * Created by jean_letard on 19/10/2016.
 */
public interface Deck {

    Building filonDor = Building.builder(Age.I, Building.Couleur.MARRON)
            .withProduction(Resource.Minerai)
            .build();

    Building timberYard = Building.builder(Age.I, Building.Couleur.MARRON)
            .withProductionChoisie(Resource.Pierre, Resource.Bois)
            .withCoutPiecesOr(1)
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
            .withCoutResources(Resource.Minerai)
            .setBoucliersMilitaires(1)
            .build();

    Building apothiquaire = Building.builder(Age.I, Building.Couleur.VERT)
            .withCoutResources(Resource.Tissu)
            .setSymboleScience(SymboleScience.Compas)
            .build();


    Building scriptotium = Building.builder(Age.I, Building.Couleur.VERT)
            .setSymboleScience(SymboleScience.PierreDeRosette)
            .withCoutResources(Resource.Papirus)
            .build();

    //Age 2
    Building hotelDeVille = Building.builder(Age.II, Building.Couleur.BLEU)
            .withCoutResources(Resource.Brique, Resource.Brique, Resource.Tissu)
            .withCoutChainage(scriptotium)
            .withPointsVictoire(4)
            .build();

}
