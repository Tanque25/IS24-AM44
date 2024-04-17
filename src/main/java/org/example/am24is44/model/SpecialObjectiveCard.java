package org.example.am24is44.model;

public class SpecialObjectiveCard extends ObjectiveCard {
    private SpecialObject[] objective;

    /**
     * SpecialObjectiveCar' constructor
     * @param cardPoints
     * @param objective
     */
    public SpecialObjectiveCard(CardPoints cardPoints, SpecialObject[] objective) {
        super(cardPoints,3);
        this.objective = objective;
    }

    public SpecialObject[] getObjective() {
        return objective;
    }
}
