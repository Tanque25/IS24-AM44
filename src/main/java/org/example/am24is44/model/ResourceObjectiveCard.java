package org.example.am24is44.model;

public class ResourceObjectiveCard extends ObjectiveCard {
    private Resource[] objective;

    /**
     * ResourceObjectiveCard's constructor
     * @param cardPoints
     * @param objective
     */
    public ResourceObjectiveCard(CardPoints cardPoints, Resource[] objective) {
        super(cardPoints,2);
        this.objective = objective;
    }

    public Resource[] getObjective() {
        return objective;
    }
}
