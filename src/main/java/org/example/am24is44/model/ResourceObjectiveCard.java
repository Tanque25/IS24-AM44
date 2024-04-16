package org.example.am24is44.model;

public class ResourceObjectiveCard extends ObjectiveCard {
    private Resource[] objective;

    /**
     * ResourceObjectiveCard's constructor
     * @param cardPoints
     * @param objective
     */
    public ResourceObjectiveCard(CardPoints cardPoints, Resource[] objective) {
        super(cardPoints);
        this.objective = objective;
    }
}
