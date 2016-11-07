package domaine;

/**
 * Created by jean_letard on 18/10/2016.
 */
public enum Age {
    I(SensRotation.GAUCHE),
    II(SensRotation.DROITE),
    III(SensRotation.GAUCHE);

    final SensRotation sensRotation;

    Age(SensRotation sensRotation) {
       this.sensRotation = sensRotation;
    }

    public enum SensRotation {GAUCHE, DROITE}

}
