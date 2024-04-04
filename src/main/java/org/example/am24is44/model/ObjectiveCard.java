package org.example.am24is44.model;

public abstract class ObjectiveCard {
    private CardPoints cardPoints;

    public ObjectiveCard(CardPoints cardPoints) {
        this.cardPoints = cardPoints;
    }

    // Method to get the card points
    public CardPoints getCardPoints() {
        return this.cardPoints;
    }
}