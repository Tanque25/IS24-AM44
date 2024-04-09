package org.example.am24is44.model;

import java.util.Map;

public class GoldCard extends Card {
    private Resource[] cost;

    // Constructor
    public GoldCard(Resource[] resource, CardPoints cardPoints, Map<CornerPosition, Corner> corners, Resource[] cost) {
        super(resource, cardPoints, corners);
        this.cost = cost;
    }

    // Method to get the cost
    public Resource[] getCost() {
        // Method implementation
        return this.cost;
    }

}
