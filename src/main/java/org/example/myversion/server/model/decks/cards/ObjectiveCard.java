package org.example.myversion.server.model.decks.cards;

import org.example.myversion.server.model.enumerations.CornerContent;
import org.example.myversion.server.model.enumerations.Resource;
import org.example.myversion.server.model.enumerations.SpecialObject;

import java.util.Map;
/**
 * Represents an abstract objective card.
 * Contains an int attribute specifying the points awarded to the player upon accomplishing its objective.
 */
public abstract class ObjectiveCard {
    private final int cardPoints;

    /**
     * ObjectiveCard's constructor.
     * @param cardPoints specifies the points awarded to the player upon accomplishing its objective.
     */
    public ObjectiveCard(int cardPoints) {
        this.cardPoints = cardPoints;
    }

    /**
     * Gets the ObjectiveCard's points.
     * @return cardPoints: points awarded to the player upon accomplishing its objective.
     */
    public int getCardPoints() {
        return cardPoints;
    }

    protected abstract CornerContent[] getCardKey();

    public abstract int findObjectiveCard(Map<CornerContent, Integer> stock, Card[][] playArea, ObjectiveCard commonObjective);
}
