package org.example.myversion.server.model.decks;

import org.example.myversion.server.model.decks.cards.Card;
import org.example.myversion.server.model.decks.cards.Corner;
import org.example.myversion.server.model.decks.cards.GoldCard;
import org.example.myversion.server.model.decks.cards.PlayableCard;
import org.example.myversion.server.model.enumerations.CornerPosition;
import org.example.myversion.server.model.enumerations.CornerVisibility;
import org.example.myversion.server.model.enumerations.Resource;
import org.example.myversion.server.model.enumerations.SpecialObject;

import java.util.Collections;
import java.util.Map;
import java.util.Stack;
import java.util.zip.CRC32;

/**
 * Represents a deck of resource cards used in the game.
 * Resource cards contain various combinations of resources and corner configurations.
 * Players can acquire resource cards to build their play area and achieve objectives.
 */
public class ResourceDeck {
    private Stack<PlayableCard> resourceDeck;

    /**
     * Constructs a ResourceDeck and initializes it with resource cards.
     */
    public ResourceDeck() {
        this.resourceDeck = new Stack<PlayableCard>();
        initializeDeck();
    }

    /**
     * Retrieves the resource deck.
     *
     * @return the resource deck.
     */
    public Stack<PlayableCard> getResourceDeck() {
        return resourceDeck;
    }

    /**
     * Initializes the resource deck with a set of predefined resource cards.
     * Each resource card is created with specific resources and corners.
     * After initialization, the deck is shuffled to randomize the order of cards.
     */
    private void initializeDeck() {
        resourceDeck.push(new PlayableCard(
                Resource.FUNGI_KINGDOM,
                Map.of(
                        CornerPosition.UP_LEFT,new Corner(Resource.FUNGI_KINGDOM),
                        CornerPosition.UP_RIGHT,new Corner(CornerVisibility.EMPTY),
                        CornerPosition.BOTTOM_LEFT,new Corner(Resource.FUNGI_KINGDOM),
                        CornerPosition.BOTTOM_RIGHT,new Corner(CornerVisibility.FULL)
                ),
                0, 001
        )); // 1

        resourceDeck.push(new PlayableCard(
                Resource.FUNGI_KINGDOM,
                Map.of(
                        CornerPosition.UP_LEFT,new Corner(Resource.FUNGI_KINGDOM),
                        CornerPosition.UP_RIGHT,new Corner(Resource.FUNGI_KINGDOM),
                        CornerPosition.BOTTOM_LEFT,new Corner(CornerVisibility.FULL),
                        CornerPosition.BOTTOM_RIGHT,new Corner(CornerVisibility.EMPTY)
                ),
                0, 002
        )); // 2

        resourceDeck.push(new PlayableCard(
                Resource.FUNGI_KINGDOM,
                Map.of(
                        CornerPosition.UP_LEFT,new Corner(CornerVisibility.EMPTY),
                        CornerPosition.UP_RIGHT,new Corner(CornerVisibility.FULL),
                        CornerPosition.BOTTOM_LEFT,new Corner(Resource.FUNGI_KINGDOM),
                        CornerPosition.BOTTOM_RIGHT,new Corner(Resource.FUNGI_KINGDOM)
                ),
                0, 003
        )); // 3

        resourceDeck.push(new PlayableCard(
                Resource.FUNGI_KINGDOM,
                Map.of(
                        CornerPosition.UP_LEFT,new Corner(CornerVisibility.FULL),
                        CornerPosition.UP_RIGHT,new Corner(Resource.FUNGI_KINGDOM),
                        CornerPosition.BOTTOM_LEFT,new Corner(CornerVisibility.EMPTY),
                        CornerPosition.BOTTOM_RIGHT,new Corner(Resource.FUNGI_KINGDOM)
                ),
                0, 004
        )); // 4

        resourceDeck.push(new PlayableCard(
                Resource.FUNGI_KINGDOM,
                Map.of(
                        CornerPosition.UP_LEFT,new Corner(CornerVisibility.FULL),
                        CornerPosition.UP_RIGHT,new Corner(SpecialObject.QUILL),
                        CornerPosition.BOTTOM_LEFT,new Corner(Resource.PLANT_KINGDOM),
                        CornerPosition.BOTTOM_RIGHT,new Corner(Resource.FUNGI_KINGDOM)
                ),
                0, 005
        )); // 5

        resourceDeck.push(new PlayableCard(
                Resource.FUNGI_KINGDOM,
                Map.of(
                        CornerPosition.UP_LEFT,new Corner(SpecialObject.INKWELL),
                        CornerPosition.UP_RIGHT,new Corner(Resource.FUNGI_KINGDOM),
                        CornerPosition.BOTTOM_LEFT,new Corner(CornerVisibility.FULL),
                        CornerPosition.BOTTOM_RIGHT,new Corner(Resource.ANIMAL_KINGDOM)
                ),
                0, 006
        )); // 6

        resourceDeck.push(new PlayableCard(
                Resource.FUNGI_KINGDOM,
                Map.of(
                        CornerPosition.UP_LEFT,new Corner(Resource.FUNGI_KINGDOM),
                        CornerPosition.UP_RIGHT,new Corner(Resource.INSECT_KINGDOM),
                        CornerPosition.BOTTOM_LEFT,new Corner(SpecialObject.MANUSCRIPT),
                        CornerPosition.BOTTOM_RIGHT,new Corner(CornerVisibility.EMPTY)
                ),
                0, 007
        )); // 7

        resourceDeck.push(new PlayableCard(
                Resource.FUNGI_KINGDOM,
                Map.of(
                        CornerPosition.UP_LEFT,new Corner(CornerVisibility.EMPTY),
                        CornerPosition.UP_RIGHT,new Corner(Resource.FUNGI_KINGDOM),
                        CornerPosition.BOTTOM_LEFT,new Corner(CornerVisibility.EMPTY),
                        CornerPosition.BOTTOM_RIGHT,new Corner(CornerVisibility.FULL)
                ),
                1, 8
        )); // 8

        resourceDeck.push(new PlayableCard(
                Resource.FUNGI_KINGDOM,
                Map.of(
                        CornerPosition.UP_LEFT,new Corner(Resource.FUNGI_KINGDOM),
                        CornerPosition.UP_RIGHT,new Corner(CornerVisibility.FULL),
                        CornerPosition.BOTTOM_LEFT,new Corner(CornerVisibility.EMPTY),
                        CornerPosition.BOTTOM_RIGHT,new Corner(CornerVisibility.EMPTY)
                ),
                1, 9
        )); // 9

        resourceDeck.push(new PlayableCard(
                Resource.FUNGI_KINGDOM,
                Map.of(
                        CornerPosition.UP_LEFT,new Corner(CornerVisibility.FULL),
                        CornerPosition.UP_RIGHT,new Corner(CornerVisibility.EMPTY),
                        CornerPosition.BOTTOM_LEFT,new Corner(Resource.FUNGI_KINGDOM),
                        CornerPosition.BOTTOM_RIGHT,new Corner(CornerVisibility.EMPTY)
                ),
                1, 010
        )); // 10

        resourceDeck.push(new PlayableCard(
                Resource.PLANT_KINGDOM,
                Map.of(
                        CornerPosition.UP_LEFT,new Corner(Resource.PLANT_KINGDOM),
                        CornerPosition.UP_RIGHT,new Corner(CornerVisibility.EMPTY),
                        CornerPosition.BOTTOM_LEFT,new Corner(Resource.PLANT_KINGDOM),
                        CornerPosition.BOTTOM_RIGHT,new Corner(CornerVisibility.FULL)
                ),
                0, 011
        )); // 11

        resourceDeck.push(new PlayableCard(
                Resource.PLANT_KINGDOM,
                Map.of(
                        CornerPosition.UP_LEFT,new Corner(Resource.PLANT_KINGDOM),
                        CornerPosition.UP_RIGHT,new Corner(Resource.PLANT_KINGDOM),
                        CornerPosition.BOTTOM_LEFT,new Corner(CornerVisibility.FULL),
                        CornerPosition.BOTTOM_RIGHT,new Corner(CornerVisibility.EMPTY)
                ),
                0, 012
        )); // 12

        resourceDeck.push(new PlayableCard(
                Resource.PLANT_KINGDOM,
                Map.of(
                        CornerPosition.UP_LEFT,new Corner(CornerVisibility.EMPTY),
                        CornerPosition.UP_RIGHT,new Corner(CornerVisibility.FULL),
                        CornerPosition.BOTTOM_LEFT,new Corner(Resource.PLANT_KINGDOM),
                        CornerPosition.BOTTOM_RIGHT,new Corner(Resource.PLANT_KINGDOM)
                ),
                0, 013
        )); // 13

        resourceDeck.push(new PlayableCard(
                Resource.PLANT_KINGDOM,
                Map.of(
                        CornerPosition.UP_LEFT,new Corner(CornerVisibility.FULL),
                        CornerPosition.UP_RIGHT,new Corner(Resource.PLANT_KINGDOM),
                        CornerPosition.BOTTOM_LEFT,new Corner(CornerVisibility.EMPTY),
                        CornerPosition.BOTTOM_RIGHT,new Corner(Resource.PLANT_KINGDOM)
                ),
                0, 014
        )); // 14

        resourceDeck.push(new PlayableCard(
                Resource.PLANT_KINGDOM,
                Map.of(
                        CornerPosition.UP_LEFT,new Corner(CornerVisibility.FULL),
                        CornerPosition.UP_RIGHT,new Corner(Resource.INSECT_KINGDOM),
                        CornerPosition.BOTTOM_LEFT,new Corner(SpecialObject.QUILL),
                        CornerPosition.BOTTOM_RIGHT,new Corner(Resource.PLANT_KINGDOM)
                ),
                0, 015
        )); // 15

        resourceDeck.push(new PlayableCard(
                Resource.PLANT_KINGDOM,
                Map.of(
                        CornerPosition.UP_LEFT,new Corner(Resource.FUNGI_KINGDOM),
                        CornerPosition.UP_RIGHT,new Corner(Resource.PLANT_KINGDOM),
                        CornerPosition.BOTTOM_LEFT,new Corner(CornerVisibility.FULL),
                        CornerPosition.BOTTOM_RIGHT,new Corner(SpecialObject.INKWELL)
                ),
                0, 016
        )); // 16

        resourceDeck.push(new PlayableCard(
                Resource.PLANT_KINGDOM,
                Map.of(
                        CornerPosition.UP_LEFT,new Corner(SpecialObject.MANUSCRIPT),
                        CornerPosition.UP_RIGHT,new Corner(CornerVisibility.FULL),
                        CornerPosition.BOTTOM_LEFT,new Corner(Resource.PLANT_KINGDOM),
                        CornerPosition.BOTTOM_RIGHT,new Corner(Resource.ANIMAL_KINGDOM)
                ),
                0, 017
        )); // 17

        resourceDeck.push(new PlayableCard(
                Resource.PLANT_KINGDOM,
                Map.of(
                        CornerPosition.UP_LEFT,new Corner(CornerVisibility.EMPTY),
                        CornerPosition.UP_RIGHT,new Corner(CornerVisibility.EMPTY),
                        CornerPosition.BOTTOM_LEFT,new Corner(Resource.PLANT_KINGDOM),
                        CornerPosition.BOTTOM_RIGHT,new Corner(CornerVisibility.FULL)
                ),
                1, 18
        )); // 18

        resourceDeck.push(new PlayableCard(
                Resource.PLANT_KINGDOM,
                Map.of(
                        CornerPosition.UP_LEFT,new Corner(CornerVisibility.EMPTY),
                        CornerPosition.UP_RIGHT,new Corner(CornerVisibility.EMPTY),
                        CornerPosition.BOTTOM_LEFT,new Corner(CornerVisibility.FULL),
                        CornerPosition.BOTTOM_RIGHT,new Corner(Resource.PLANT_KINGDOM)
                ),
                1, 19
        )); // 19

        resourceDeck.push(new PlayableCard(
                Resource.PLANT_KINGDOM,
                Map.of(
                        CornerPosition.UP_LEFT,new Corner(CornerVisibility.FULL),
                        CornerPosition.UP_RIGHT,new Corner(Resource.PLANT_KINGDOM),
                        CornerPosition.BOTTOM_LEFT,new Corner(CornerVisibility.EMPTY),
                        CornerPosition.BOTTOM_RIGHT,new Corner(CornerVisibility.EMPTY)
                ),
                1, 020
        )); // 20

        resourceDeck.push(new PlayableCard(
                Resource.ANIMAL_KINGDOM,
                Map.of(
                        CornerPosition.UP_LEFT,new Corner(Resource.ANIMAL_KINGDOM),
                        CornerPosition.UP_RIGHT,new Corner(Resource.ANIMAL_KINGDOM),
                        CornerPosition.BOTTOM_LEFT,new Corner(CornerVisibility.EMPTY),
                        CornerPosition.BOTTOM_RIGHT,new Corner(CornerVisibility.FULL)
                ),
                0, 021
        )); // 21

        resourceDeck.push(new PlayableCard(
                Resource.ANIMAL_KINGDOM,
                Map.of(
                        CornerPosition.UP_LEFT,new Corner(CornerVisibility.FULL),
                        CornerPosition.UP_RIGHT,new Corner(CornerVisibility.EMPTY),
                        CornerPosition.BOTTOM_LEFT,new Corner(Resource.ANIMAL_KINGDOM),
                        CornerPosition.BOTTOM_RIGHT,new Corner(Resource.ANIMAL_KINGDOM)
                ),
                0, 022
        )); // 22

        resourceDeck.push(new PlayableCard(
                Resource.ANIMAL_KINGDOM,
                Map.of(
                        CornerPosition.UP_LEFT,new Corner(Resource.ANIMAL_KINGDOM),
                        CornerPosition.UP_RIGHT,new Corner(CornerVisibility.FULL),
                        CornerPosition.BOTTOM_LEFT,new Corner(Resource.ANIMAL_KINGDOM),
                        CornerPosition.BOTTOM_RIGHT,new Corner(CornerVisibility.EMPTY)
                ),
                0, 023
        )); // 23

        resourceDeck.push(new PlayableCard(
                Resource.ANIMAL_KINGDOM,
                Map.of(
                        CornerPosition.UP_LEFT,new Corner(CornerVisibility.EMPTY),
                        CornerPosition.UP_RIGHT,new Corner(Resource.ANIMAL_KINGDOM),
                        CornerPosition.BOTTOM_LEFT,new Corner(CornerVisibility.FULL),
                        CornerPosition.BOTTOM_RIGHT,new Corner(Resource.ANIMAL_KINGDOM)
                ),
                0, 024
        )); // 24

        resourceDeck.push(new PlayableCard(
                Resource.ANIMAL_KINGDOM,
                Map.of(
                        CornerPosition.UP_LEFT,new Corner(CornerVisibility.FULL),
                        CornerPosition.UP_RIGHT,new Corner(Resource.INSECT_KINGDOM),
                        CornerPosition.BOTTOM_LEFT,new Corner(SpecialObject.INKWELL),
                        CornerPosition.BOTTOM_RIGHT,new Corner(Resource.ANIMAL_KINGDOM)
                ),
                0, 025
        )); // 25

        resourceDeck.push(new PlayableCard(
                Resource.ANIMAL_KINGDOM,
                Map.of(
                        CornerPosition.UP_LEFT,new Corner(Resource.PLANT_KINGDOM),
                        CornerPosition.UP_RIGHT,new Corner(Resource.ANIMAL_KINGDOM),
                        CornerPosition.BOTTOM_LEFT,new Corner(CornerVisibility.FULL),
                        CornerPosition.BOTTOM_RIGHT,new Corner(SpecialObject.MANUSCRIPT)
                ),
                0, 026
        )); // 26

        resourceDeck.push(new PlayableCard(
                Resource.ANIMAL_KINGDOM,
                Map.of(
                        CornerPosition.UP_LEFT,new Corner(SpecialObject.QUILL),
                        CornerPosition.UP_RIGHT,new Corner(CornerVisibility.FULL),
                        CornerPosition.BOTTOM_LEFT,new Corner(Resource.ANIMAL_KINGDOM),
                        CornerPosition.BOTTOM_RIGHT,new Corner(Resource.FUNGI_KINGDOM)
                ),
                0, 027
        )); // 27

        resourceDeck.push(new PlayableCard(
                Resource.ANIMAL_KINGDOM,
                Map.of(
                        CornerPosition.UP_LEFT,new Corner(CornerVisibility.FULL),
                        CornerPosition.UP_RIGHT,new Corner(CornerVisibility.EMPTY),
                        CornerPosition.BOTTOM_LEFT,new Corner(Resource.ANIMAL_KINGDOM),
                        CornerPosition.BOTTOM_RIGHT,new Corner(CornerVisibility.EMPTY)
                ),
                1, 28
        )); // 28

        resourceDeck.push(new PlayableCard(
                Resource.ANIMAL_KINGDOM,
                Map.of(
                        CornerPosition.UP_LEFT,new Corner(CornerVisibility.EMPTY),
                        CornerPosition.UP_RIGHT,new Corner(CornerVisibility.FULL),
                        CornerPosition.BOTTOM_LEFT,new Corner(CornerVisibility.EMPTY),
                        CornerPosition.BOTTOM_RIGHT,new Corner(Resource.ANIMAL_KINGDOM)
                ),
                1, 29
        )); // 29

        resourceDeck.push(new PlayableCard(
                Resource.ANIMAL_KINGDOM,
                Map.of(
                        CornerPosition.UP_LEFT,new Corner(CornerVisibility.EMPTY),
                        CornerPosition.UP_RIGHT,new Corner(Resource.ANIMAL_KINGDOM),
                        CornerPosition.BOTTOM_LEFT,new Corner(CornerVisibility.EMPTY),
                        CornerPosition.BOTTOM_RIGHT,new Corner(CornerVisibility.FULL)
                ),
                1, 030
        )); // 30

        resourceDeck.push(new PlayableCard(
                Resource.INSECT_KINGDOM,
                Map.of(
                        CornerPosition.UP_LEFT,new Corner(Resource.INSECT_KINGDOM),
                        CornerPosition.UP_RIGHT,new Corner(Resource.INSECT_KINGDOM),
                        CornerPosition.BOTTOM_LEFT,new Corner(CornerVisibility.EMPTY),
                        CornerPosition.BOTTOM_RIGHT,new Corner(CornerVisibility.FULL)
                ),
                0, 031
        )); // 31

        resourceDeck.push(new PlayableCard(
                Resource.INSECT_KINGDOM,
                Map.of(
                        CornerPosition.UP_LEFT,new Corner(CornerVisibility.FULL),
                        CornerPosition.UP_RIGHT,new Corner(CornerVisibility.EMPTY),
                        CornerPosition.BOTTOM_LEFT,new Corner(Resource.INSECT_KINGDOM),
                        CornerPosition.BOTTOM_RIGHT,new Corner(Resource.INSECT_KINGDOM)
                ),
                0, 032
        )); // 32

        resourceDeck.push(new PlayableCard(
                Resource.INSECT_KINGDOM,
                Map.of(
                        CornerPosition.UP_LEFT,new Corner(Resource.INSECT_KINGDOM),
                        CornerPosition.UP_RIGHT,new Corner(CornerVisibility.FULL),
                        CornerPosition.BOTTOM_LEFT,new Corner(Resource.INSECT_KINGDOM),
                        CornerPosition.BOTTOM_RIGHT,new Corner(CornerVisibility.EMPTY)
                ),
                0, 033
        )); // 33

        resourceDeck.push(new PlayableCard(
                Resource.INSECT_KINGDOM,
                Map.of(
                        CornerPosition.UP_LEFT,new Corner(CornerVisibility.EMPTY),
                        CornerPosition.UP_RIGHT,new Corner(Resource.INSECT_KINGDOM),
                        CornerPosition.BOTTOM_LEFT,new Corner(CornerVisibility.FULL),
                        CornerPosition.BOTTOM_RIGHT,new Corner(Resource.INSECT_KINGDOM)
                ),
                0, 034
        )); // 34

        resourceDeck.push(new PlayableCard(
                Resource.INSECT_KINGDOM,
                Map.of(
                        CornerPosition.UP_LEFT,new Corner(CornerVisibility.FULL),
                        CornerPosition.UP_RIGHT,new Corner(SpecialObject.QUILL),
                        CornerPosition.BOTTOM_LEFT,new Corner(Resource.ANIMAL_KINGDOM),
                        CornerPosition.BOTTOM_RIGHT,new Corner(Resource.INSECT_KINGDOM)
                ),
                0, 035
        )); // 35

        resourceDeck.push(new PlayableCard(
                Resource.INSECT_KINGDOM,
                Map.of(
                        CornerPosition.UP_LEFT,new Corner(SpecialObject.MANUSCRIPT),
                        CornerPosition.UP_RIGHT,new Corner(Resource.INSECT_KINGDOM),
                        CornerPosition.BOTTOM_LEFT,new Corner(CornerVisibility.FULL),
                        CornerPosition.BOTTOM_RIGHT,new Corner(Resource.FUNGI_KINGDOM)
                ),
                0, 036
        )); // 36

        resourceDeck.push(new PlayableCard(
                Resource.INSECT_KINGDOM,
                Map.of(
                        CornerPosition.UP_LEFT,new Corner(Resource.INSECT_KINGDOM),
                        CornerPosition.UP_RIGHT,new Corner(Resource.PLANT_KINGDOM),
                        CornerPosition.BOTTOM_LEFT,new Corner(SpecialObject.INKWELL),
                        CornerPosition.BOTTOM_RIGHT,new Corner(CornerVisibility.FULL)
                ),
                0, 037
        )); // 37

        resourceDeck.push(new PlayableCard(
                Resource.INSECT_KINGDOM,
                Map.of(
                        CornerPosition.UP_LEFT,new Corner(Resource.INSECT_KINGDOM),
                        CornerPosition.UP_RIGHT,new Corner(CornerVisibility.FULL),
                        CornerPosition.BOTTOM_LEFT,new Corner(CornerVisibility.EMPTY),
                        CornerPosition.BOTTOM_RIGHT,new Corner(CornerVisibility.EMPTY)
                ),
                1, 38
        )); // 38

        resourceDeck.push(new PlayableCard(
                Resource.INSECT_KINGDOM,
                Map.of(
                        CornerPosition.UP_LEFT,new Corner(CornerVisibility.EMPTY),
                        CornerPosition.UP_RIGHT,new Corner(CornerVisibility.EMPTY),
                        CornerPosition.BOTTOM_LEFT,new Corner(CornerVisibility.FULL),
                        CornerPosition.BOTTOM_RIGHT,new Corner(Resource.INSECT_KINGDOM)
                ),
                1, 39
        )); // 39

        resourceDeck.push(new PlayableCard(
                Resource.INSECT_KINGDOM,
                Map.of(
                        CornerPosition.UP_LEFT,new Corner(CornerVisibility.EMPTY),
                        CornerPosition.UP_RIGHT,new Corner(CornerVisibility.EMPTY),
                        CornerPosition.BOTTOM_LEFT,new Corner(CornerVisibility.FULL),
                        CornerPosition.BOTTOM_RIGHT,new Corner(Resource.INSECT_KINGDOM)
                ),
                1, 40
        )); // 40

        Collections.shuffle(resourceDeck);
    }

    /**
     * Removes the top card from the resource deck and returns it.
     *
     * @return the resource card on the top of the resource deck.
     */
    public PlayableCard drawCard() {
        return resourceDeck.pop();
    }
}
