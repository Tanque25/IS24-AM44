package org.example.am24is44.model;

import java.util.Map;

public class Card {
    private Resource[] resource;
    private Map<CornerPosition, Corner> corners;
    private Map<CornerPosition, Card> connections;
    private CardPoints cardPoints;
    private boolean playedBack;

    // Constructor
    public Card(Resource[] resource, Map<CornerPosition, Corner> corners, Map<CornerPosition, Card> connections, CardPoints cardPoints) {
        this.resource = resource;
        this.corners = corners;
        this.connections = connections;
        this.cardPoints = cardPoints;
    }

    // Getter for resource
    public Resource[] getResource() {
        return this.resource;
    }

    // Method to get corner by position
    public Corner getCorner(CornerPosition position) {
        return corners.get(position);
    }

    // Method to get connection by corner position
    public Card getConnection(CornerPosition position) {
        return connections.get(position);
    }

    // Method to get card points
    public CardPoints getCardPoints() {
        return this.cardPoints;
    }

    // Method to check if card has been played back
    public boolean playedBack() {
        return playedBack;
    }

}
