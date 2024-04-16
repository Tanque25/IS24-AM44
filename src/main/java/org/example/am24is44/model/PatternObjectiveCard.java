package org.example.am24is44.model;

public class PatternObjectiveCard extends ObjectiveCard {
    private Resource[][] objective;

    /**
     * PatternObjectiveCard's constructor
     * @param cardPoints
     * @param objective
     */
    public PatternObjectiveCard(CardPoints cardPoints, Resource[][] objective) {
        super(cardPoints);
        this.objective = objective;
    }
}
