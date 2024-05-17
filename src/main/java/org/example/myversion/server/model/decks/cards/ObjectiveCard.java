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


    private final int id;

    /**
     * ObjectiveCard's constructor.
     * @param cardPoints specifies the points awarded to the player upon accomplishing its objective.
     */
    public ObjectiveCard(int cardPoints, int id) {
        this.cardPoints = cardPoints;
        this.id = id;
    }

    /**
     * Gets the ObjectiveCard's points.
     * @return cardPoints: points awarded to the player upon accomplishing its objective.
     */
    public int getCardPoints() {
        return cardPoints;
    }

    public int getId() {
        return id;
    }

    protected abstract CornerContent[] getCardKey();

    public abstract int findObjectiveCard(Map<CornerContent, Integer> stock, Card[][] playArea, ObjectiveCard commonObjective);
}
