package org.example.am24is44.model;

public abstract class ObjectiveCard {
    private CardPoints cardPoints;

    private int flag=0;
    /**
     * ObjectiveCard's constructor
     * @param cardPoints
     */
    public ObjectiveCard(CardPoints cardPoints,int flag) {

        this.cardPoints = cardPoints;
        this.flag=flag;
    }

    /**
     * Method to get the card points
     * @return cardPoints
     */
    public CardPoints getCardPoints() {
        return this.cardPoints;
    }

    public int getFlag() {
        return flag;
    }

}