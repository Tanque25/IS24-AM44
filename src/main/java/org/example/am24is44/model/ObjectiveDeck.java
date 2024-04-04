package org.example.am24is44.model;

import static org.example.am24is44.model.CardPoints.*;
import static org.example.am24is44.model.Resource.*;

public class ObjectiveDeck {
    private ObjectiveCard[] objectiveCards;

    // Constructor
    public ObjectiveDeck() {
    }

    // Method to set objective cards
    public void setCards() {
        // first three objective cards
        Resource[][] objective87 = new Resource[3][3];
        objective87[0][2] = FUNGI_KINGDOM;
        objective87[1][1] = FUNGI_KINGDOM;
        objective87[2][0] = FUNGI_KINGDOM;
        PatternObjectiveCard c87 = new PatternObjectiveCard(TWO, objective87);
        this.objectiveCards[0] = c87;

        Resource[][] objective88 = new Resource[3][3];
        objective88[0][0] = PLANT_KINGDOM;
        objective88[1][1] = PLANT_KINGDOM;
        objective88[2][2] = PLANT_KINGDOM;
        PatternObjectiveCard c88 = new PatternObjectiveCard(TWO, objective88);
        this.objectiveCards[1] = c88;

        Resource[][] objective89 = new Resource[3][3];
        objective89[0][2] = ANIMAL_KINGDOM;
        objective89[1][1] = ANIMAL_KINGDOM;
        objective89[2][0] = ANIMAL_KINGDOM;
        PatternObjectiveCard c89 = new PatternObjectiveCard(TWO, objective89);
        this.objectiveCards[2] = c89;
    }

    // Method to get objective cards
    public ObjectiveCard[] getCards() {
        return objectiveCards;
    }

    // Method to remove an objective card
    public void removeCard(ObjectiveCard card) {
        if (objectiveCards.length == 0) {
            // Manage the empty deck case
            return;
        }

        // Creation of a new array with a smaller size
        ObjectiveCard[] newObjectiveCards = new ObjectiveCard[objectiveCards.length - 1];

        // Copy all the cards except from the last one
        for (int i = 0; i < newObjectiveCards.length; i++) {
            newObjectiveCards[i] = objectiveCards[i];
        }

        objectiveCards = newObjectiveCards;
    }
}
