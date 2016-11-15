package api;

import java.util.List;

/**
 * Created by jean_letard on 15/11/2016.
 */
public class PlayerRepresentation {

    private String name;
    private List<CardRepresentation> cartesConstruites;
    private List<Integer> jetonsMilitaires;
    private int nombreDePièces;
    private String voisinGauche;
    private String voisinDroite;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<CardRepresentation> getCartesConstruites() {
        return cartesConstruites;
    }

    public void setCartesConstruites(List<CardRepresentation> cartesConstruites) {
        this.cartesConstruites = cartesConstruites;
    }

    public List<Integer> getJetonsMilitaires() {
        return jetonsMilitaires;
    }

    public void setJetonsMilitaires(List<Integer> jetonsMilitaires) {
        this.jetonsMilitaires = jetonsMilitaires;
    }

    public int getNombreDePièces() {
        return nombreDePièces;
    }

    public void setNombreDePièces(int nombreDePièces) {
        this.nombreDePièces = nombreDePièces;
    }

    public String getVoisinGauche() {
        return voisinGauche;
    }

    public void setVoisinGauche(String voisinGauche) {
        this.voisinGauche = voisinGauche;
    }

    public String getVoisinDroite() {
        return voisinDroite;
    }

    public void setVoisinDroite(String voisinDroite) {
        this.voisinDroite = voisinDroite;
    }
}
