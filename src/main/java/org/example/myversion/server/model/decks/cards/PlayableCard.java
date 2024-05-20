package org.example.myversion.server.model.decks.cards;

import org.example.myversion.server.model.enumerations.CornerPosition;
import org.example.myversion.server.model.enumerations.Resource;

import java.util.Map;

/**
 * Represents a resource card in the game.
 * Resource cards contain a single type of resource and are used by players to build their play area and achieve objectives.
 */
public class PlayableCard extends Card{
    private final Resource resource;
    private final Integer cardPoints;

    /**
     * Constructs a resource card with the specified resource, corners, and points.
     *
     * @param resource   The resource associated with the card.
     * @param corners    The map containing corners and their positions on the card.
     * @param cardPoints The points awarded to the player upon playing the card.
     */
    public PlayableCard(Resource resource, Map<CornerPosition, Corner> corners, Integer cardPoints, Integer id) {
        super(corners, id);
        this.resource = resource;
        this.cardPoints = cardPoints;
    }

    /**
     * Retrieves the resource associated with the card.
     *
     * @return The resource of the card.
     */


    public Resource getResource() {
        return resource;
    }

    @Override
    public Resource getPlayableResource(){
        return resource;
    }

    /**
     * Retrieves the points awarded to the player upon playing the card.
     *
     * @return the card points.
     */
    public int getCardPoints() {
        return cardPoints;
    }
}
