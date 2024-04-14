package org.example.am24is44.model;

import java.util.Map;

public class GoldCard extends Card {
    private Resource[] cost;
    private SpecialObject multiplier;

    // Constructor
    public GoldCard(Resource[] resource, CardPoints cardPoints, Map<CornerPosition, Corner> corners, Resource[] cost, SpecialObject multiplier) {
        super(resource, cardPoints, corners);
        this.cost = cost;
        this.multiplier = multiplier;

    }

    // Method to get the cost
    public Resource[] getCost() {
        // Method implementation
        return this.cost;
    }

    public Integer calculatePoints(){
        // Da implementare, idealmente si passerà
        // la playArea al metodo per calcolare
        // in base al bonus della carta
        int extraPoints = 0;
        return extraPoints;
    }

}
