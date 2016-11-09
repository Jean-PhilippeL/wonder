package domaine;

/**
 * Created by jean_letard on 18/10/2016.
 */
public enum Age {
    I(SensRotation.GAUCHE, 1),
    II(SensRotation.DROITE,3),
    III(SensRotation.GAUCHE,5);

    public final SensRotation sensRotation;
    public final int nbPointDefaiteMilitaire = -1;
    public final int nbPointVictoireMilitaire;

    Age(SensRotation sensRotation, int nbPointVictoireMilitaire) {
       this.sensRotation = sensRotation;
        this.nbPointVictoireMilitaire = nbPointVictoireMilitaire;
    }

    public enum SensRotation {GAUCHE, DROITE}

}
