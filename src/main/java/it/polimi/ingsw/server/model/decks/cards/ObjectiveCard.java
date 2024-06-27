package it.polimi.ingsw.server.model.decks.cards;

import it.polimi.ingsw.server.model.enumerations.CornerContent;

import java.util.Map;

/**
 * Represents an abstract objective card.
 * Contains an int attribute specifying the points awarded to the player upon accomplishing its objective.
 */
public abstract class ObjectiveCard {
    private final int cardPoints;
    private final Integer id;

    /**
     * ObjectiveCard's constructor.
     *
     * @param cardPoints specifies the points awarded to the player upon accomplishing its objective.
     */
    public ObjectiveCard(int cardPoints, Integer id) {
        this.cardPoints = cardPoints;
        this.id = id;
    }

    /**
     * Gets the ObjectiveCard's points.
     *
     * @return cardPoints: points awarded to the player upon accomplishing its objective.
     */
    public int getCardPoints() {
        return cardPoints;
    }

    public Integer getId() {
        return id;
    }

    protected abstract CornerContent[] getCardKey();

    public abstract int calculateObjectiveCardPoints(Map<CornerContent, Integer> stock, Card[][] playArea, ObjectiveCard commonObjective);
}
