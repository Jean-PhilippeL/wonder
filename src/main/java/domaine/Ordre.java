package domaine;

import service.Action;

import java.util.List;
import java.util.Map;

/**
 * Created by jean_letard on 10/11/2016.
 */
public class Ordre {
    Joueur joueur;
    Action action;
    Building carteChoisie;
    Map<Joueur,Map<Building, List<Resource>>> selectionRessources;
}
