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
        objective87[0][0] = FUNGI_KINGDOM;
        objective87[1][1] = FUNGI_KINGDOM;
        objective87[2][2] = FUNGI_KINGDOM;
        PatternObjectiveCard c87 = new PatternObjectiveCard(TWO, objective87);
        objectiveCards.push(c87);

        // Card 88
        Resource[][] objective88 = new Resource[3][3];
        objective88[0][2] = PLANT_KINGDOM;
        objective88[1][1] = PLANT_KINGDOM;
        objective88[2][0] = PLANT_KINGDOM;
        PatternObjectiveCard c88 = new PatternObjectiveCard(TWO, objective88);
        objectiveCards.push(c88);

        // Card 89
        Resource[][] objective89 = new Resource[3][3];
        objective89[0][0] = ANIMAL_KINGDOM;
        objective89[1][1] = ANIMAL_KINGDOM;
        objective89[2][2] = ANIMAL_KINGDOM;
        PatternObjectiveCard c89 = new PatternObjectiveCard(TWO, objective89);
        objectiveCards.push(c89);

        //Card 90
        Resource[][] objective90 = new Resource[3][3];
        objective90[0][2] = INSECT_KINGDOM;
        objective90[1][1] = INSECT_KINGDOM;
        objective90[2][0] = INSECT_KINGDOM;
        PatternObjectiveCard c90 = new PatternObjectiveCard(TWO, objective90);
        objectiveCards.push(c90);

        //card 91
        Resource[][] objective91 = new Resource[3][3];
        objective90[0][2] = FUNGI_KINGDOM;
        objective90[0][1] = FUNGI_KINGDOM;
        objective90[1][0] = PLANT_KINGDOM;
        PatternObjectiveCard c91 = new PatternObjectiveCard(THREE, objective91);
        objectiveCards.push(c91);

        //card 92
        Resource[][] objective92 = new Resource[3][3];
        objective90[1][2] = PLANT_KINGDOM;
        objective90[1][1] = PLANT_KINGDOM;
        objective90[0][0] = INSECT_KINGDOM;
        PatternObjectiveCard c92 = new PatternObjectiveCard(THREE, objective92);
        objectiveCards.push(c92);

        //card 93
        Resource[][] objective93 = new Resource[3][3];
        objective90[1][2] = FUNGI_KINGDOM;
        objective90[0][1] = FUNGI_KINGDOM;
        objective90[0][0] = ANIMAL_KINGDOM;
        PatternObjectiveCard c93 = new PatternObjectiveCard(THREE, objective93);
        objectiveCards.push(c93);

        //card 94
        Resource[][] objective94 = new Resource[3][3];
        objective90[0][2] = ANIMAL_KINGDOM;
        objective90[1][1] = INSECT_KINGDOM;
        objective90[1][0] = INSECT_KINGDOM;
        PatternObjectiveCard c94 = new PatternObjectiveCard(THREE, objective94);
        objectiveCards.push(c94);

        //card95

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
