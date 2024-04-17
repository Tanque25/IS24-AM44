package org.example.am24is44.model;
import static org.example.am24is44.model.Resource.*;
import static org.example.am24is44.model.CornerPosition.*;
import java.util.Map;
import java.util.HashMap;

public class Card {
    private Resource[] resource;

    private Map<CornerPosition, Corner> corners;
    private Map<CornerPosition, Card> connections; //da vedere
    private CardPoints cardPoints;
    private boolean playedBack;
    //private int i;


    /**
     * Card's constructor
     * @param resource
     * @param cardPoints
     * @param corners
     */
    public Card(Resource[] resource, CardPoints cardPoints, Map<CornerPosition, Corner> corners) {
        this.resource = resource;
        this.corners = corners;
        this.cardPoints = cardPoints;
    }

    /**
     * Getter for resource
     * @return this.resource
     */
    public Resource[] getResource() {
        return this.resource;
    }

    /**
     * Method to get corner by position
     * @param position
     * @return corners.get(position)
     */
    public Corner getCorner(CornerPosition position) {
        return corners.get(position);
    }

    /**
     * Method to get connection by corner position
     * @param position
     * @return connections.get(position)
     */
    public Card getConnection(CornerPosition position) {
        return connections.get(position);
    }

    /**
     *
     * @param resource
     * @param i
     */
    public void setResource(Resource resource,int i) {
        this.resource[i] = resource;
    }

    /**
     *
     * @param corners
     */
    public void setCorners(Map<CornerPosition, Corner> corners) {
        this.corners = corners;
    }

    /**
     * Method to get card points
     * @return this.cardPoints
     */
    public CardPoints getCardPoints() {
        return this.cardPoints;
    }

    /**
     * Method to check if card has been played back
     * @return playedBack a boolean
     */
    public boolean playedBack() {
        return playedBack;
    }

    public boolean isGoldCard(){return false;}



}
