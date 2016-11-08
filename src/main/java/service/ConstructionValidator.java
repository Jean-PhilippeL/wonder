package service;

import domaine.Building;
import domaine.Joueur;
import domaine.Resource;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Created by jean_letard on 20/10/2016.
 */
public class ConstructionValidator {

    public static class Structure {
        Joueur joueur;
        Map<Building, List<Resource>> ressourcesMap;
    }

    //Note : ou alors on ne fait que la verif, les transactions aura lieu plus tard...
    public void validerAchat(Building building, Structure ressourceJoueur, Optional<Structure> ressourceVoisinGauche, Optional<Structure> ressourceVoisinDroit){

        //Verif que les resources sont bien existantes
        possede(ressourceJoueur.joueur, ressourceJoueur.ressourcesMap);

        //Vérif que l'on achète bien à ses voisins directs et que ces derniers ont assez de ressources
        ressourceVoisinGauche.ifPresent(resource -> {
                if(resource.joueur != ressourceJoueur.joueur.getVoisinGauche()){
                    throw new RuntimeException("On ne peut acheter qu'a ses voisins directs");
                }

                voisinPossede(resource.joueur, resource.ressourcesMap);
        });

        ressourceVoisinDroit.ifPresent(resource -> {
            if(resource.joueur != ressourceJoueur.joueur.getVoisinDroite()){
                throw new RuntimeException("On ne peut acheter qu'a ses voisins directs");
            }
            voisinPossede(resource.joueur, resource.ressourcesMap);
        });

        //Verif que le joueur a assez d'argent pour payer les ressources à ses voisins
        final long[] coutVoisins = {0};

        ressourceVoisinGauche.ifPresent(resource -> coutVoisins[0] +=coutResources(resource));
        ressourceVoisinDroit.ifPresent(resource -> coutVoisins[0] +=coutResources(resource));

        if(ressourceJoueur.joueur.getNombreDePièces() < coutVoisins[0]){
            throw new RuntimeException("pas assez d'argent pour acheter les ressources au voisin");
        }

        if(ressourceJoueur.joueur.getNombreDePièces() < coutVoisins[0] + building.getCoutPiecesOr()){
            throw new RuntimeException("pas assez d'argent pour acheter le batiment");
        }

        //Vérif que les ressources suffisantes ont été rassemblées pour construire le batiment

        final List<Resource> allRessources = new ArrayList<>();
        allRessources.addAll(collecterRessources(ressourceJoueur));
        ressourceVoisinGauche.ifPresent(resource -> allRessources.addAll(collecterRessources(resource)));
        ressourceVoisinDroit.ifPresent(resource -> allRessources.addAll(collecterRessources(resource)));

        for(Resource ressource : Resource.values()){
            if(countByType(building.getCoutRessources(), ressource) > countByType(allRessources, ressource)){
                throw new RuntimeException("pas assez de " + ressource + " pour acheter le batiment");
            }
        }

        //On a les resources et l'argent, c'est validé !
    }

    private  List<Resource> collecterRessources(Structure structure){
        return structure.ressourcesMap.values().stream().
                flatMap(List::stream)
                .collect(Collectors.toList());
    }

    private long coutResources(Structure structure){
        return structure.ressourcesMap.values().stream().
                flatMap(List::stream)
                .count() * 2; //TODO : Utilisation du commerce
    }


    private void voisinPossede(Joueur joueur, Map<Building, List<Resource>> ressourcesMap){
        if (ressourcesMap.keySet().stream().anyMatch(b -> b.getCouleur()!= Building.Couleur.MARRON || b.getCouleur()!= Building.Couleur.GRIS )){
            throw new RuntimeException("seules les ressources des cartes marrons et grises sont achetables");
        }
        possede(joueur, ressourcesMap);
    }

    //Valide si le joueur à le droit d'utiliser légitimement les ressources
    private void possede(Joueur joueur, Map<Building, List<Resource>> ressourcesMap) {

        if (!joueur.getCartesConstruites().containsAll(ressourcesMap.keySet())) {
            throw new RuntimeException("le joueur ne possède pas toutes les cartes");
        }

        for (Map.Entry<Building, List<Resource>> entry : ressourcesMap.entrySet()) {

            final Building building = entry.getKey();
            final List<Resource> resources = entry.getValue();

            if (!joueur.getCartesConstruites().contains(building)) {
                throw new RuntimeException("le joueur ne possède pas la carte " + building);
            }

            if (!building.getProductionChoisie().isEmpty()) {
                if (resources.size() > 2) {
                    throw new RuntimeException("On ne peut pas utiliser toutes les resources, il faut faire un choix");
                }

                if (!building.getProductionChoisie().containsAll(resources)) {
                    throw new RuntimeException("Ce batiment ne produit pas de " + resources);
                }
            } else if (!building.getProduction().isEmpty()) {
                for (Resource resource : Resource.values()){
                    if(countByType(building.getProduction(), resource) < countByType(resources, resource)){
                        throw new RuntimeException("Ce batiment ne produit pas assez de " + resource);
                    }
                }
            } else {
                throw new RuntimeException("Ce batiment ne produit rien");
            }
        }
    }

    private long countByType(List<Resource> resources, Resource resource){
        return resources.stream().filter(r -> r==resource).count();
    }

}
