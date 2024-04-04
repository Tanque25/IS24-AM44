package org.example.am24is44.model;

import java.util.Map;

public class GoldCard extends Card {
    private Resource[] cost;

    // Constructor
    public GoldCard(Resource[] resource, Map<CornerPosition, Corner> corners, Map<CornerPosition, Card> connections, CardPoints cardPoints, Resource[] cost) {
        super(resource, corners, connections, cardPoints);
        this.cost = cost;
    }

    // Method to get the cost
    public Resource[] getCost() {
        // Method implementation
        return this.cost;
    }

}
