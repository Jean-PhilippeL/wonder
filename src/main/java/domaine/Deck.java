package domaine;

/**
 * TODO : revoir la manière d'instancier les cartes... Enum ou autre, il faut qu'elles soient accessibles unitairement pour les tests
 */
@Deprecated
public interface Deck {

    Building filonDor = Building.builder("filonDor",Age.I, Building.Couleur.MARRON)
            .withProduction(Resource.Minerai)
            .build();

    Building timberYard = Building.builder("timberYard",Age.I, Building.Couleur.MARRON)
            .withProductionChoisie(Resource.Pierre, Resource.Bois)
            .withCoutPiecesOr(1)
            .build();

    Building verrerie = Building.builder("verrerie" ,Age.I, Building.Couleur.GRIS)
            .withProduction(Resource.Verre)
            .build();

    Building taverne = Building.builder("taverne" , Age.I, Building.Couleur.JAUNE)
            .withOnConstructMethode(j -> j.ajouterPieces(5) )
            .build();

    Building pownshop = Building.builder("pownshop", Age.I, Building.Couleur.BLEU)
            .withPointsVictoire(3)
            .build();

    Building tourDeGarde = Building.builder("tourDeGarde", Age.I, Building.Couleur.ROUGE)
            .withCoutResources(Resource.Minerai)
            .setBoucliersMilitaires(1)
            .build();

    Building apothiquaire = Building.builder("apothiquaire", Age.I, Building.Couleur.VERT)
            .withCoutResources(Resource.Tissu)
            .setSymboleScience(SymboleScience.Compas)
            .build();


    Building scriptotium = Building.builder("scriptotium", Age.I, Building.Couleur.VERT)
            .setSymboleScience(SymboleScience.PierreDeRosette)
            .withCoutResources(Resource.Papirus)
            .build();

    //domaine.Age 2
    Building hotelDeVille = Building.builder("hotelDeVille", Age.II, Building.Couleur.BLEU)
            .withCoutResources(Resource.Brique, Resource.Brique, Resource.Tissu)
            .withCoutChainage(scriptotium)
            .withPointsVictoire(4)
            .build();

}
