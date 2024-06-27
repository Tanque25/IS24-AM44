package it.polimi.ingsw.server.model.decks.cards;

import it.polimi.ingsw.server.model.enumerations.CornerPosition;
import it.polimi.ingsw.server.model.enumerations.PointsParameter;
import it.polimi.ingsw.server.model.enumerations.Resource;

import java.util.Map;

/**
 * Represents a gold card in the game.
 * Gold cards are special cards that players can play depending on the number of resources in their play area and provide additional points.
 */
public class GoldCard extends PlayableCard{
    private final Resource[] cost;
    private final PointsParameter pointsParameter;

    /**
     * Constructs a gold card with the specified resources, corners, points, cost, and points parameter.
     *
     * @param resource        the array of resources associated with the card.
     * @param corners         the map containing corners and their positions on the card.
     * @param cardPoints      the points awarded to the player upon playing the card.
     * @param cost            the array of resources required to play the gold card.
     * @param pointsParameter the parameter indicating how the gold card provides additional points.
     */
    public GoldCard(Resource resource, Map<CornerPosition, Corner> corners, int cardPoints, Resource[] cost, PointsParameter pointsParameter, Integer id) {
        super(resource, corners, cardPoints, id);
        this.cost = cost;
        this.pointsParameter = pointsParameter;
    }

    /**
     * Retrieves the array of resources required to play the gold card.
     *
     * @return the array of resources representing the cost of the gold card.
     */
    public Resource[] getCost() {
        return cost;
    }

    /**
     * Retrieves the parameter indicating how the gold card provides additional points.
     *
     * @return the points parameter of the gold card.
     */
    public PointsParameter getPointsParameter() {
        return pointsParameter;
    }
}
