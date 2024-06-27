package it.polimi.ingsw.server.model.decks.cards;

import it.polimi.ingsw.server.model.enumerations.CornerPosition;
import it.polimi.ingsw.server.model.enumerations.Resource;

import java.util.Map;

/**
 * Represents a starter card in the game.
 * Each player gets a starter card in the beginning of the game.
 * Starter cards have additional back corners compared to regular cards since they have a content.
 */
public class StarterCard extends Card {
    private final Resource[] resource;
    private final Map<CornerPosition, Corner> backCorners;

    /**
     * Constructs a starter card with the specified resources, corners, points, and back corners.
     *
     * @param corners      the map containing corners and their positions on the card.
     * @param resource     the array of resources associated with the card, relevant if played on the front.
     * @param backCorners  the map containing corners and their positions on the back side of the card.
     */
    public StarterCard(Resource[] resource, Map<CornerPosition, Corner> corners, Map<CornerPosition, Corner> backCorners, int id) {
        super(corners, id);
        this.resource = resource;
        this.backCorners = backCorners;
    }

    /**
     * Retrieves the array of resources associated with the starter card.
     * It consists of multiple resources in the starter cards only.
     *
     * @return the array of resources.
     */
    public Resource[] getResource() {
        return resource;
    }

    @Override
    public Resource getPlayableResource(){
        return null;
    }

    public Map<CornerPosition, Corner> getBackCorner() {
        return backCorners;
    }
}
