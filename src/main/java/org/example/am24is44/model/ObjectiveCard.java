package org.example.am24is44.model;

public abstract class ObjectiveCard {
    private CardPoints cardPoints;

    /**
     * ObjectiveCard's constructor
     * @param cardPoints
     */
    public ObjectiveCard(CardPoints cardPoints) {
        this.cardPoints = cardPoints;
    }

    /**
     * Method to get the card points
     * @return this.cardPoints
     */
    public CardPoints getCardPoints() {
        return this.cardPoints;
    }
}