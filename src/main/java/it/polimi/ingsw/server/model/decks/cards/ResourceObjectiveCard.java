package it.polimi.ingsw.server.model.decks.cards;

import it.polimi.ingsw.server.model.enumerations.CornerContent;
import it.polimi.ingsw.server.model.enumerations.Resource;

import java.util.Map;

/**
 * Represents a resource objective card.
 * It specifies which and how many resources the player needs to have in his play area.
 */
public class ResourceObjectiveCard extends ObjectiveCard {
    private final Resource[] objective;

    /**
     * Constructs a ResourceObjectiveCard with specified points and objective.
     *
     * @param cardPoints specifies the points awarded to the player upon accomplishing its objective.
     * @param objective  specifies which and how many resources the player needs to have in his play area.
     */
    public ResourceObjectiveCard(int cardPoints, Resource[] objective, int id) {
        super(cardPoints, id);
        this.objective = objective;
    }

    /**
     * Retrieves the resource objective.
     *
     * @return The array specifying which and how many resources the player needs to have in their play area.
     */
    @Override
    public CornerContent[] getCardKey() {
        return objective;
    }

    public Resource[] getObjective() {
        return objective;
    }

    /**
     * Calculates the points gained by a player by achieving a resource objective.
     *
     * @param stock           resources stock of the player
     * @param playArea        play area of the player
     * @param objective objective card to consider
     * @return points gained.
     */
    @Override
    public int calculateObjectiveCardPoints(Map<CornerContent, Integer> stock, Card[][] playArea, ObjectiveCard objective) {
        CornerContent[] objectiveResources = objective.getCardKey();
        int objectiveCardPoints = objective.getCardPoints();

        // find the player's stock of the objective's resource
        int stockCount = stock.getOrDefault(objectiveResources[0], 0);

        // return the given points
        if (stockCount >= objectiveResources.length) {
            return ((stockCount / objectiveResources.length) * objectiveCardPoints);
        }

        return 0;
    }
}
