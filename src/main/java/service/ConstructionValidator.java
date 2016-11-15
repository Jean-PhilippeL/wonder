package service;

import com.google.common.collect.Iterables;
import domaine.Building;
import domaine.Joueur;
import domaine.Resource;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by jean_letard on 20/10/2016.
 */
public class ConstructionValidator {

    public static class Structure {
        Joueur joueur;
        Map<Building, List<Resource>> selectionRessources;
    }

    //Note : ou alors on ne fait que la verif, les transactions aura lieu plus tard...
    public void validerAchat(Joueur joueur, Building building, Map<Joueur,Map<Building, List<Resource>>> selectionRessources){

        //Vérif que les joueurs designés sont bien les bons (le joueur lui-même ou ses voisins)
        List<Joueur> otherPlayers = selectionRessources.keySet().stream().filter(j -> j == joueur || j == joueur.getVoisinDroite() || j == joueur.getVoisinGauche()).collect(Collectors.toList());
        if(!otherPlayers.isEmpty()){
            throw new RuntimeException("impossible d'acheter a ce joueur " + Iterables.getFirst(otherPlayers, null));
        }

        //Verif que les cartes des voisins sont bien grises ou marron

        if(selectionRessources.entrySet().stream().filter(entry -> entry.getKey() != joueur).flatMap(entry -> entry.getValue().keySet().stream()).anyMatch(c -> c.getCouleur() != Building.Couleur.GRIS || c.getCouleur() != Building.Couleur.MARRON)){
            throw new RuntimeException("impossible de prendre les resources des voisins sur des cartes pas grises ou marron ");
        }

        //Verif que les resources sont bien existantes
        selectionRessources.entrySet().forEach(entry -> possede(entry.getKey(), entry.getValue()));

        //Verif que le joueur a assez d'argent pour payer les ressources à ses voisins
        final List<Resource> ressourcesVoisins = selectionRessources.values().stream().flatMap(e -> e.values().stream()).flatMap(e -> e.stream()).collect(Collectors.toList());
        final int coutVoisins = coutResources(ressourcesVoisins);

        if(joueur.getNombreDePièces() < coutVoisins){
            throw new RuntimeException("pas assez d'argent pour acheter les ressources aux voisins");
        }

        if(joueur.getNombreDePièces() < coutVoisins + building.getCoutPiecesOr()){
            throw new RuntimeException("pas assez d'argent pour acheter le batiment");
        }

        //Vérif que les ressources suffisantes ont été rassemblées pour construire le batiment
        final List<Resource> allRessources = selectionRessources.values().stream().flatMap(e -> e.values().stream()).flatMap(e -> e.stream()).collect(Collectors.toList());

        for(Resource ressource : Resource.values()){
            if(countByType(building.getCoutRessources(), ressource) > countByType(allRessources, ressource)){
                throw new RuntimeException("pas assez de " + ressource + " pour acheter le batiment");
            }
        }

        //On a les resources et l'argent, c'est validé !
    }

    private int coutResources(List<Resource> resources){
        return resources.size() * 2; //TODO : Utilisation du commerce
    }

    //Valide si le joueur à le droit d'utiliser légitimement les ressources
    private void possede(Joueur joueur, Map<Building, List<Resource>> ressourcesMap) {

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
                throw new RuntimeException("Ce batiment ne produit rien");
            } else {
                for (Resource resource : Resource.values()){
                    if(countByType(building.getProduction(), resource) < countByType(resources, resource)){
                        throw new RuntimeException("Ce batiment ne produit pas assez de " + resource);
                    }
                }
            }
        }
    }

    private long countByType(List<Resource> resources, Resource resource){
        return resources.stream().filter(r -> r==resource).count();
    }

}
