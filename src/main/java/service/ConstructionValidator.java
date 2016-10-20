package service;

import domaine.Building;
import domaine.Joueur;
import domaine.Resource;

import java.util.List;
import java.util.Map;

/**
 * Created by jean_letard on 20/10/2016.
 */
public class ConstructionValidator {


    public void voisinPossede(Joueur joueur, Map<Building, List<Resource>> ressourcesMap){
        if (ressourcesMap.keySet().stream().anyMatch(b -> b.getCouleur()!= Building.Couleur.MARRON || b.getCouleur()!= Building.Couleur.GRIS )){
            throw new RuntimeException("seules les ressources des cartes marrons et grises sont achetables");
        }
        possede(joueur, ressourcesMap);
    }

    //Valide si le joueur à le droit d'utiliser légitimement les ressources
    public void possede(Joueur joueur, Map<Building, List<Resource>> ressourcesMap) {

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
