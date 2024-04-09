package org.example.am24is44.model;

import static org.example.am24is44.model.CardPoints.*;
import static org.example.am24is44.model.Resource.*;

import java.util.Collections;
import java.util.Stack;

public class ObjectiveDeck {
    private Stack<ObjectiveCard> objectiveCards;

    // Constructor
    public ObjectiveDeck() {
        this.objectiveCards = new Stack<>();
    }

    // Method to set objective cards
    public void setCards() {
        // Card 87
        Resource[][] objective87 = new Resource[3][3];
        objective87[0][2] = FUNGI_KINGDOM;
        objective87[1][1] = FUNGI_KINGDOM;
        objective87[2][0] = FUNGI_KINGDOM;
        PatternObjectiveCard c87 = new PatternObjectiveCard(TWO, objective87);
        objectiveCards.push(c87);

        // Card 88
        Resource[][] objective88 = new Resource[3][3];
        objective88[0][0] = PLANT_KINGDOM;
        objective88[1][1] = PLANT_KINGDOM;
        objective88[2][2] = PLANT_KINGDOM;
        PatternObjectiveCard c88 = new PatternObjectiveCard(TWO, objective88);
        objectiveCards.push(c88);

        // Card 89
        Resource[][] objective89 = new Resource[3][3];
        objective89[0][2] = ANIMAL_KINGDOM;
        objective89[1][1] = ANIMAL_KINGDOM;
        objective89[2][0] = ANIMAL_KINGDOM;
        PatternObjectiveCard c89 = new PatternObjectiveCard(TWO, objective89);
        objectiveCards.push(c89);

        // Next cards...

        Collections.shuffle(objectiveCards);
    }

    // Method to get objective cards
    public Stack<ObjectiveCard> getCards() {
        return objectiveCards;
    }

    // Method to remove an objective card
    public ObjectiveCard drawCard() {

        if (objectiveCards.empty()) {
            // Manage the empty deck case
        }

        // pop a card and return it
        return objectiveCards.pop();
    }
}
