package org.example.am24is44.model;

public class SpecialObjectiveCard extends ObjectiveCard {
    private SpecialObject[] objective;

    /**
     * SpecialObjectiveCard's constructor
     * @param cardPoints
     * @param objective
     */
    public SpecialObjectiveCard(CardPoints cardPoints, SpecialObject[] objective) {
        super(cardPoints);
        this.objective = objective;
    }
}
