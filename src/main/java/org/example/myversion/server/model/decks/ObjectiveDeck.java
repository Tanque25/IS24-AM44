package org.example.myversion.server.model.decks;

import org.example.myversion.server.model.decks.cards.*;
import org.example.myversion.server.model.enumerations.Resource;
import org.example.myversion.server.model.enumerations.SpecialObject;

import java.util.Collections;
import java.util.Stack;

/**
 * Represents a deck of objective cards used in the game.
 * Objective cards can include card patterns or specific resource or special object objectives that players aim to achieve.
 */
public class ObjectiveDeck {
    private Stack<ObjectiveCard> objectiveDeck;

    /**
     * Constructs an ObjectiveDeck and initializes it with objective cards.
     */
    public ObjectiveDeck() {
        this.objectiveDeck = new Stack<ObjectiveCard>();
        initializeDeck();
    }

    /**
     * Initializes the objective deck with a set of predefined objective cards.
     * These cards include patterns, resource objectives, and special object objectives.
     * Each card is assigned a specific number of points that players can earn upon accomplishing its objective.
     * After initialization, the deck is shuffled to randomize the order of cards.
     */
    private void initializeDeck(){
        addPatternObjectiveCard(2, new Resource[][]{
                {null, null, Resource.FUNGI_KINGDOM},
                {null, Resource.FUNGI_KINGDOM, null},
                {Resource.FUNGI_KINGDOM, null, null}
        }, 87); // 87

        addPatternObjectiveCard(2, new Resource[][]{
                {Resource.PLANT_KINGDOM, null, null},
                {null, Resource.PLANT_KINGDOM, null},
                {null, null, Resource.PLANT_KINGDOM}
        }, 88); // 88

        addPatternObjectiveCard(2, new Resource[][]{
                {null, null, Resource.ANIMAL_KINGDOM},
                {null, Resource.ANIMAL_KINGDOM, null},
                {Resource.ANIMAL_KINGDOM, null, null}
        }, 89); // 89

        addPatternObjectiveCard(2, new Resource[][]{
                {Resource.INSECT_KINGDOM, null, null},
                {null, Resource.INSECT_KINGDOM, null},
                {null, null, Resource.INSECT_KINGDOM}
        }, 90); // 90

        addPatternObjectiveCard(3, new Resource[][]{
                {Resource.FUNGI_KINGDOM, null, null},
                {null, null, null},
                {Resource.FUNGI_KINGDOM, null, null},
                { null, Resource.PLANT_KINGDOM, null}
        }, 91); // 91

        addPatternObjectiveCard(3, new Resource[][]{
                {null, Resource.PLANT_KINGDOM, null},
                {null, null, null},
                {null, Resource.PLANT_KINGDOM, null},
                {Resource.INSECT_KINGDOM, null, null}
        }, 92); // 92

        addPatternObjectiveCard(3, new Resource[][]{
                { null, Resource.FUNGI_KINGDOM, null},
                {Resource.ANIMAL_KINGDOM, null, null},
                {null, null, null},
                {Resource.ANIMAL_KINGDOM, null, null}
        }, 93); // 93

        addPatternObjectiveCard(3, new Resource[][]{
                {Resource.ANIMAL_KINGDOM, null, null},
                {null, Resource.INSECT_KINGDOM, null},
                {null, null, null},
                {null, Resource.INSECT_KINGDOM, null}
        }, 94); // 94

        addResourceObjectiveCard(2, new Resource[]{
                Resource.FUNGI_KINGDOM, Resource.FUNGI_KINGDOM, Resource.FUNGI_KINGDOM}, 95
        ); // 95

        addResourceObjectiveCard(2, new Resource[]{
                Resource.PLANT_KINGDOM, Resource.PLANT_KINGDOM, Resource.PLANT_KINGDOM}, 96
        ); // 96

        addResourceObjectiveCard(2, new Resource[]{
                Resource.ANIMAL_KINGDOM, Resource.ANIMAL_KINGDOM, Resource.ANIMAL_KINGDOM}, 97
        ); // 97

        addResourceObjectiveCard(2, new Resource[]{
                Resource.INSECT_KINGDOM, Resource.INSECT_KINGDOM, Resource.INSECT_KINGDOM}, 98
        ); // 98

        addSpecialObjectiveCard(3, new SpecialObject[]{
                SpecialObject.QUILL, SpecialObject.INKWELL, SpecialObject.MANUSCRIPT}, 99
        ); // 99

        addSpecialObjectiveCard(2, new SpecialObject[]{
                SpecialObject.MANUSCRIPT, SpecialObject.MANUSCRIPT}, 100
        ); // 100

        addSpecialObjectiveCard(2, new SpecialObject[]{
                SpecialObject.INKWELL, SpecialObject.INKWELL}, 101
        ); // 101

        addSpecialObjectiveCard(2, new SpecialObject[]{
                SpecialObject.QUILL, SpecialObject.QUILL}, 102
        ); // 102

        Collections.shuffle(objectiveDeck);
    }

    /**
     * Create a new resource objective card with the specified parameters.
     * Add the new card to the objective deck.
     *
     * @param cardPoints specifies the points awarded to the player upon accomplishing its objective.
     * @param objective specifies the pattern that the player has to recreate in his play area.
     */
    private void addPatternObjectiveCard(int cardPoints, Resource[][] objective, int id) {
        PatternObjectiveCard card = new PatternObjectiveCard(cardPoints, objective, id);
        objectiveDeck.push(card);
    }

    /**
     * Create a new resource objective card with the specified parameters.
     * Add the new card to the objective deck.
     *
     * @param cardPoints specifies the points awarded to the player upon accomplishing its objective.
     * @param objective specifies which and how many resources the player needs to have in his play area.
     */
    private void addResourceObjectiveCard(int cardPoints, Resource[] objective, int id) {
        ResourceObjectiveCard card = new ResourceObjectiveCard(cardPoints, objective, id);
        objectiveDeck.push(card);
    }

    /**
     * Create a new special object objective card with the specified parameters.
     * Add the new card to the objective deck.
     *
     * @param cardPoints specifies the points awarded to the player upon accomplishing its objective.
     * @param objective specifies which and how many special objects the player needs to have in his play area.
     */
    private void addSpecialObjectiveCard(int cardPoints, SpecialObject[] objective, int id) {
        SpecialObjectiveCard card = new SpecialObjectiveCard(cardPoints, objective, id);
        objectiveDeck.push(card);
    }

    /**
     * Removes the card on the top of the objective deck and returns it.
     *
     * @return the objective card on the top of the objective deck.
     */
    public ObjectiveCard drawCard(){
        return objectiveDeck.pop();
    }

    /**
     * Retrieves the objective deck.
     *
     * @return the objective deck.
     */
    public Stack<ObjectiveCard> getObjectiveDeck() {
        return objectiveDeck;
    }
}
