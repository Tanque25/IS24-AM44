package org.example.am24is44.model;
import static org.example.am24is44.model.Resource.*;
import static org.example.am24is44.model.CornerPosition.*;
import java.util.Map;
import java.util.HashMap;

public class Card {
    private Resource[] resource;

    private Map<CornerPosition, Corner> corners;
    private Map<CornerPosition, Card> connections;
    private CardPoints cardPoints;
    private boolean playedBack;
    //private int i;

    // Constructor
    public Card(Resource[] resource, CardPoints cardPoints,Map<CornerPosition, Corner> corners) {

        this.resource = resource;
        this.corners=corners;
        //this.corners = new Map<CornerPosition, Corner>;
        //this.connections = new Map<CornerPosition, Card>;
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

    public void setResource(Resource resource,int i) {
        this.resource[i] = resource;
    }

    public void setCorners(Map<CornerPosition, Corner> corners) {
        this.corners = corners;
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
