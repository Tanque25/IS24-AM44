package org.example.am24is44.model;

public class SpecialObjectiveCard extends ObjectiveCard {
    private SpecialObject[] objective;

    public SpecialObjectiveCard(CardPoints cardPoints, SpecialObject[] objective) {
        super(cardPoints);
        this.objective = objective;
    }
}
