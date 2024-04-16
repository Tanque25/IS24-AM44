package org.example.am24is44.model;

import static org.example.am24is44.model.CardPoints.*;
import static org.example.am24is44.model.Resource.*;
import static org.example.am24is44.model.SpecialObject.*;

import java.util.Collections;
import java.util.Stack;

public class ObjectiveDeck {
    private Stack<ObjectiveCard> objectiveCards;

    /**
     * ObjectiveDeck's constructor
     */
    public ObjectiveDeck() {
        this.objectiveCards = new Stack<>();
    }

    /**
     * Method to set objective cards
     */
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
        objective91[0][2] = FUNGI_KINGDOM;
        objective91[0][1] = FUNGI_KINGDOM;
        objective91[1][0] = PLANT_KINGDOM;
        PatternObjectiveCard c91 = new PatternObjectiveCard(THREE, objective91);
        objectiveCards.push(c91);

        //card 92
        Resource[][] objective92 = new Resource[3][3];
        objective92[1][2] = PLANT_KINGDOM;
        objective92[1][1] = PLANT_KINGDOM;
        objective92[0][0] = INSECT_KINGDOM;
        PatternObjectiveCard c92 = new PatternObjectiveCard(THREE, objective92);
        objectiveCards.push(c92);

        //card 93
        Resource[][] objective93 = new Resource[3][3];
        objective93[1][2] = FUNGI_KINGDOM;
        objective93[0][1] = FUNGI_KINGDOM;
        objective93[0][0] = ANIMAL_KINGDOM;
        PatternObjectiveCard c93 = new PatternObjectiveCard(THREE, objective93);
        objectiveCards.push(c93);

        //card 94
        Resource[][] objective94 = new Resource[3][3];
        objective94[0][2] = ANIMAL_KINGDOM;
        objective94[1][1] = INSECT_KINGDOM;
        objective94[1][0] = INSECT_KINGDOM;
        PatternObjectiveCard c94 = new PatternObjectiveCard(THREE, objective94);
        objectiveCards.push(c94);

        //card95
        Resource[] objective95 = new Resource[]{FUNGI_KINGDOM,FUNGI_KINGDOM,FUNGI_KINGDOM};
        ResourceObjectiveCard c95 = new ResourceObjectiveCard(TWO, objective95);
        objectiveCards.push(c95);

        //card96
        Resource[] objective96 = new Resource[]{PLANT_KINGDOM,PLANT_KINGDOM,PLANT_KINGDOM};
        ResourceObjectiveCard c96 = new ResourceObjectiveCard(TWO, objective96);
        objectiveCards.push(c96);

        //card97
        Resource[] objective97 = new Resource[]{ANIMAL_KINGDOM,ANIMAL_KINGDOM,ANIMAL_KINGDOM};
        ResourceObjectiveCard c97 = new ResourceObjectiveCard(TWO, objective97);
        objectiveCards.push(c97);

        //card98
        Resource[] objective98 = new Resource[]{INSECT_KINGDOM,INSECT_KINGDOM,INSECT_KINGDOM};
        ResourceObjectiveCard c98 = new ResourceObjectiveCard(TWO, objective98);
        objectiveCards.push(c98);

        //card99
        SpecialObject[] objective99 = new SpecialObject[]{QUILL,INKWELL,MANUSCRIPT};
        SpecialObjectiveCard c99 = new SpecialObjectiveCard(THREE, objective99);
        objectiveCards.push(c99);

        //card100
        SpecialObject[] objective100 = new SpecialObject[]{MANUSCRIPT,MANUSCRIPT};
        SpecialObjectiveCard c100 = new SpecialObjectiveCard(TWO, objective100);
        objectiveCards.push(c100);

        //card101
        SpecialObject[] objective101 = new SpecialObject[]{INKWELL,INKWELL};
        SpecialObjectiveCard c101 = new SpecialObjectiveCard(TWO, objective101);
        objectiveCards.push(c101);

        //card102
        SpecialObject[] objective102 = new SpecialObject[]{QUILL,QUILL};
        SpecialObjectiveCard c102 = new SpecialObjectiveCard(TWO, objective102);
        objectiveCards.push(c102);

        Collections.shuffle(objectiveCards);
    }

    /**
     * Method to get objective cards
     * @return objectiveCards
     */
    public Stack<ObjectiveCard> getCards() {
        return objectiveCards;
    }

    /**
     * Method to remove an objective card
     * @return objectiveCards.pop()
     */
    public ObjectiveCard drawCard() {

        if (objectiveCards.empty()) {
            // Manage the empty deck case
        }

        // pop a card and return it
        return objectiveCards.pop();
    }
}
