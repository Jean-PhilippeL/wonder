package api;

import domaine.Age;

import java.util.List;

/**
 * Created by jean_letard on 15/11/2016.
 */
public class CardRepresentation {

    private String name;
    private Age age;
    private String color;
    private List<ResourceRepresentation> coutRessources;
    private int costGoldenPieces;
    private List<ResourceRepresentation> production;
    private List<ResourceRepresentation> productionChoisie;
    private int militaryShields;
    private int VictoryPoint;
    private ScientificSymbolRepresentation symboleScience;
    // TODO : indiquer le pouvoir de construction
    private String parentName;
    private String childName;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Age getAge() {
        return age;
    }

    public void setAge(Age age) {
        this.age = age;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public List<ResourceRepresentation> getCoutRessources() {
        return coutRessources;
    }

    public void setCoutRessources(List<ResourceRepresentation> coutRessources) {
        this.coutRessources = coutRessources;
    }

    public int getCostGoldenPieces() {
        return costGoldenPieces;
    }

    public void setCostGoldenPieces(int costGoldenPieces) {
        this.costGoldenPieces = costGoldenPieces;
    }

    public List<ResourceRepresentation> getProduction() {
        return production;
    }

    public void setProduction(List<ResourceRepresentation> production) {
        this.production = production;
    }

    public List<ResourceRepresentation> getProductionChoisie() {
        return productionChoisie;
    }

    public void setProductionChoisie(List<ResourceRepresentation> productionChoisie) {
        this.productionChoisie = productionChoisie;
    }

    public int getMilitaryShields() {
        return militaryShields;
    }

    public void setMilitaryShields(int militaryShields) {
        this.militaryShields = militaryShields;
    }

    public int getVictoryPoint() {
        return VictoryPoint;
    }

    public void setVictoryPoint(int victoryPoint) {
        VictoryPoint = victoryPoint;
    }

    public ScientificSymbolRepresentation getSymboleScience() {
        return symboleScience;
    }

    public void setSymboleScience(ScientificSymbolRepresentation symboleScience) {
        this.symboleScience = symboleScience;
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public String getChildName() {
        return childName;
    }

    public void setChildName(String childName) {
        this.childName = childName;
    }
}
