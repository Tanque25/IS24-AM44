package it.polimi.ingsw.server.model.decks;

import it.polimi.ingsw.server.model.decks.cards.Card;
import it.polimi.ingsw.server.model.decks.cards.Corner;
import it.polimi.ingsw.server.model.decks.cards.GoldCard;
import it.polimi.ingsw.server.model.decks.cards.PlayableCard;
import it.polimi.ingsw.server.model.enumerations.CornerPosition;
import it.polimi.ingsw.server.model.enumerations.CornerVisibility;
import it.polimi.ingsw.server.model.enumerations.Resource;
import it.polimi.ingsw.server.model.enumerations.SpecialObject;

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

    public ResourceDeck(Stack<PlayableCard> resourceDeck) {
        this.resourceDeck = resourceDeck;
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
                0, 1
        )); // 1

        resourceDeck.push(new PlayableCard(
                Resource.FUNGI_KINGDOM,
                Map.of(
                        CornerPosition.UP_LEFT,new Corner(Resource.FUNGI_KINGDOM),
                        CornerPosition.UP_RIGHT,new Corner(Resource.FUNGI_KINGDOM),
                        CornerPosition.BOTTOM_LEFT,new Corner(CornerVisibility.FULL),
                        CornerPosition.BOTTOM_RIGHT,new Corner(CornerVisibility.EMPTY)
                ),
                0, 2
        )); // 2

        resourceDeck.push(new PlayableCard(
                Resource.FUNGI_KINGDOM,
                Map.of(
                        CornerPosition.UP_LEFT,new Corner(CornerVisibility.EMPTY),
                        CornerPosition.UP_RIGHT,new Corner(CornerVisibility.FULL),
                        CornerPosition.BOTTOM_LEFT,new Corner(Resource.FUNGI_KINGDOM),
                        CornerPosition.BOTTOM_RIGHT,new Corner(Resource.FUNGI_KINGDOM)
                ),
                0, 3
        )); // 3

        resourceDeck.push(new PlayableCard(
                Resource.FUNGI_KINGDOM,
                Map.of(
                        CornerPosition.UP_LEFT,new Corner(CornerVisibility.FULL),
                        CornerPosition.UP_RIGHT,new Corner(Resource.FUNGI_KINGDOM),
                        CornerPosition.BOTTOM_LEFT,new Corner(CornerVisibility.EMPTY),
                        CornerPosition.BOTTOM_RIGHT,new Corner(Resource.FUNGI_KINGDOM)
                ),
                0, 4
        )); // 4

        resourceDeck.push(new PlayableCard(
                Resource.FUNGI_KINGDOM,
                Map.of(
                        CornerPosition.UP_LEFT,new Corner(CornerVisibility.FULL),
                        CornerPosition.UP_RIGHT,new Corner(SpecialObject.QUILL),
                        CornerPosition.BOTTOM_LEFT,new Corner(Resource.PLANT_KINGDOM),
                        CornerPosition.BOTTOM_RIGHT,new Corner(Resource.FUNGI_KINGDOM)
                ),
                0, 5
        )); // 5

        resourceDeck.push(new PlayableCard(
                Resource.FUNGI_KINGDOM,
                Map.of(
                        CornerPosition.UP_LEFT,new Corner(SpecialObject.INKWELL),
                        CornerPosition.UP_RIGHT,new Corner(Resource.FUNGI_KINGDOM),
                        CornerPosition.BOTTOM_LEFT,new Corner(CornerVisibility.FULL),
                        CornerPosition.BOTTOM_RIGHT,new Corner(Resource.ANIMAL_KINGDOM)
                ),
                0, 6
        )); // 6

        resourceDeck.push(new PlayableCard(
                Resource.FUNGI_KINGDOM,
                Map.of(
                        CornerPosition.UP_LEFT,new Corner(Resource.FUNGI_KINGDOM),
                        CornerPosition.UP_RIGHT,new Corner(Resource.INSECT_KINGDOM),
                        CornerPosition.BOTTOM_LEFT,new Corner(SpecialObject.MANUSCRIPT),
                        CornerPosition.BOTTOM_RIGHT,new Corner(CornerVisibility.EMPTY)
                ),
                0, 7
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
                1, 10
        )); // 10

        resourceDeck.push(new PlayableCard(
                Resource.PLANT_KINGDOM,
                Map.of(
                        CornerPosition.UP_LEFT,new Corner(Resource.PLANT_KINGDOM),
                        CornerPosition.UP_RIGHT,new Corner(CornerVisibility.EMPTY),
                        CornerPosition.BOTTOM_LEFT,new Corner(Resource.PLANT_KINGDOM),
                        CornerPosition.BOTTOM_RIGHT,new Corner(CornerVisibility.FULL)
                ),
                0, 11
        )); // 11

        resourceDeck.push(new PlayableCard(
                Resource.PLANT_KINGDOM,
                Map.of(
                        CornerPosition.UP_LEFT,new Corner(Resource.PLANT_KINGDOM),
                        CornerPosition.UP_RIGHT,new Corner(Resource.PLANT_KINGDOM),
                        CornerPosition.BOTTOM_LEFT,new Corner(CornerVisibility.FULL),
                        CornerPosition.BOTTOM_RIGHT,new Corner(CornerVisibility.EMPTY)
                ),
                0, 12
        )); // 12

        resourceDeck.push(new PlayableCard(
                Resource.PLANT_KINGDOM,
                Map.of(
                        CornerPosition.UP_LEFT,new Corner(CornerVisibility.EMPTY),
                        CornerPosition.UP_RIGHT,new Corner(CornerVisibility.FULL),
                        CornerPosition.BOTTOM_LEFT,new Corner(Resource.PLANT_KINGDOM),
                        CornerPosition.BOTTOM_RIGHT,new Corner(Resource.PLANT_KINGDOM)
                ),
                0, 13
        )); // 13

        resourceDeck.push(new PlayableCard(
                Resource.PLANT_KINGDOM,
                Map.of(
                        CornerPosition.UP_LEFT,new Corner(CornerVisibility.FULL),
                        CornerPosition.UP_RIGHT,new Corner(Resource.PLANT_KINGDOM),
                        CornerPosition.BOTTOM_LEFT,new Corner(CornerVisibility.EMPTY),
                        CornerPosition.BOTTOM_RIGHT,new Corner(Resource.PLANT_KINGDOM)
                ),
                0, 14
        )); // 14

        resourceDeck.push(new PlayableCard(
                Resource.PLANT_KINGDOM,
                Map.of(
                        CornerPosition.UP_LEFT,new Corner(CornerVisibility.FULL),
                        CornerPosition.UP_RIGHT,new Corner(Resource.INSECT_KINGDOM),
                        CornerPosition.BOTTOM_LEFT,new Corner(SpecialObject.QUILL),
                        CornerPosition.BOTTOM_RIGHT,new Corner(Resource.PLANT_KINGDOM)
                ),
                0, 15
        )); // 15

        resourceDeck.push(new PlayableCard(
                Resource.PLANT_KINGDOM,
                Map.of(
                        CornerPosition.UP_LEFT,new Corner(Resource.FUNGI_KINGDOM),
                        CornerPosition.UP_RIGHT,new Corner(Resource.PLANT_KINGDOM),
                        CornerPosition.BOTTOM_LEFT,new Corner(CornerVisibility.FULL),
                        CornerPosition.BOTTOM_RIGHT,new Corner(SpecialObject.INKWELL)
                ),
                0, 16
        )); // 16

        resourceDeck.push(new PlayableCard(
                Resource.PLANT_KINGDOM,
                Map.of(
                        CornerPosition.UP_LEFT,new Corner(SpecialObject.MANUSCRIPT),
                        CornerPosition.UP_RIGHT,new Corner(CornerVisibility.FULL),
                        CornerPosition.BOTTOM_LEFT,new Corner(Resource.PLANT_KINGDOM),
                        CornerPosition.BOTTOM_RIGHT,new Corner(Resource.ANIMAL_KINGDOM)
                ),
                0, 17
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
                1, 20
        )); // 20

        resourceDeck.push(new PlayableCard(
                Resource.ANIMAL_KINGDOM,
                Map.of(
                        CornerPosition.UP_LEFT,new Corner(Resource.ANIMAL_KINGDOM),
                        CornerPosition.UP_RIGHT,new Corner(Resource.ANIMAL_KINGDOM),
                        CornerPosition.BOTTOM_LEFT,new Corner(CornerVisibility.EMPTY),
                        CornerPosition.BOTTOM_RIGHT,new Corner(CornerVisibility.FULL)
                ),
                0, 21
        )); // 21

        resourceDeck.push(new PlayableCard(
                Resource.ANIMAL_KINGDOM,
                Map.of(
                        CornerPosition.UP_LEFT,new Corner(CornerVisibility.FULL),
                        CornerPosition.UP_RIGHT,new Corner(CornerVisibility.EMPTY),
                        CornerPosition.BOTTOM_LEFT,new Corner(Resource.ANIMAL_KINGDOM),
                        CornerPosition.BOTTOM_RIGHT,new Corner(Resource.ANIMAL_KINGDOM)
                ),
                0, 22
        )); // 22

        resourceDeck.push(new PlayableCard(
                Resource.ANIMAL_KINGDOM,
                Map.of(
                        CornerPosition.UP_LEFT,new Corner(Resource.ANIMAL_KINGDOM),
                        CornerPosition.UP_RIGHT,new Corner(CornerVisibility.FULL),
                        CornerPosition.BOTTOM_LEFT,new Corner(Resource.ANIMAL_KINGDOM),
                        CornerPosition.BOTTOM_RIGHT,new Corner(CornerVisibility.EMPTY)
                ),
                0, 23
        )); // 23

        resourceDeck.push(new PlayableCard(
                Resource.ANIMAL_KINGDOM,
                Map.of(
                        CornerPosition.UP_LEFT,new Corner(CornerVisibility.EMPTY),
                        CornerPosition.UP_RIGHT,new Corner(Resource.ANIMAL_KINGDOM),
                        CornerPosition.BOTTOM_LEFT,new Corner(CornerVisibility.FULL),
                        CornerPosition.BOTTOM_RIGHT,new Corner(Resource.ANIMAL_KINGDOM)
                ),
                0, 24
        )); // 24

        resourceDeck.push(new PlayableCard(
                Resource.ANIMAL_KINGDOM,
                Map.of(
                        CornerPosition.UP_LEFT,new Corner(CornerVisibility.FULL),
                        CornerPosition.UP_RIGHT,new Corner(Resource.INSECT_KINGDOM),
                        CornerPosition.BOTTOM_LEFT,new Corner(SpecialObject.INKWELL),
                        CornerPosition.BOTTOM_RIGHT,new Corner(Resource.ANIMAL_KINGDOM)
                ),
                0, 25
        )); // 25

        resourceDeck.push(new PlayableCard(
                Resource.ANIMAL_KINGDOM,
                Map.of(
                        CornerPosition.UP_LEFT,new Corner(Resource.PLANT_KINGDOM),
                        CornerPosition.UP_RIGHT,new Corner(Resource.ANIMAL_KINGDOM),
                        CornerPosition.BOTTOM_LEFT,new Corner(CornerVisibility.FULL),
                        CornerPosition.BOTTOM_RIGHT,new Corner(SpecialObject.MANUSCRIPT)
                ),
                0, 26
        )); // 26

        resourceDeck.push(new PlayableCard(
                Resource.ANIMAL_KINGDOM,
                Map.of(
                        CornerPosition.UP_LEFT,new Corner(SpecialObject.QUILL),
                        CornerPosition.UP_RIGHT,new Corner(CornerVisibility.FULL),
                        CornerPosition.BOTTOM_LEFT,new Corner(Resource.ANIMAL_KINGDOM),
                        CornerPosition.BOTTOM_RIGHT,new Corner(Resource.FUNGI_KINGDOM)
                ),
                0, 27
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
                1, 30
        )); // 30

        resourceDeck.push(new PlayableCard(
                Resource.INSECT_KINGDOM,
                Map.of(
                        CornerPosition.UP_LEFT,new Corner(Resource.INSECT_KINGDOM),
                        CornerPosition.UP_RIGHT,new Corner(Resource.INSECT_KINGDOM),
                        CornerPosition.BOTTOM_LEFT,new Corner(CornerVisibility.EMPTY),
                        CornerPosition.BOTTOM_RIGHT,new Corner(CornerVisibility.FULL)
                ),
                0, 31
        )); // 31

        resourceDeck.push(new PlayableCard(
                Resource.INSECT_KINGDOM,
                Map.of(
                        CornerPosition.UP_LEFT,new Corner(CornerVisibility.FULL),
                        CornerPosition.UP_RIGHT,new Corner(CornerVisibility.EMPTY),
                        CornerPosition.BOTTOM_LEFT,new Corner(Resource.INSECT_KINGDOM),
                        CornerPosition.BOTTOM_RIGHT,new Corner(Resource.INSECT_KINGDOM)
                ),
                0, 32
        )); // 32

        resourceDeck.push(new PlayableCard(
                Resource.INSECT_KINGDOM,
                Map.of(
                        CornerPosition.UP_LEFT,new Corner(Resource.INSECT_KINGDOM),
                        CornerPosition.UP_RIGHT,new Corner(CornerVisibility.FULL),
                        CornerPosition.BOTTOM_LEFT,new Corner(Resource.INSECT_KINGDOM),
                        CornerPosition.BOTTOM_RIGHT,new Corner(CornerVisibility.EMPTY)
                ),
                0, 33
        )); // 33

        resourceDeck.push(new PlayableCard(
                Resource.INSECT_KINGDOM,
                Map.of(
                        CornerPosition.UP_LEFT,new Corner(CornerVisibility.EMPTY),
                        CornerPosition.UP_RIGHT,new Corner(Resource.INSECT_KINGDOM),
                        CornerPosition.BOTTOM_LEFT,new Corner(CornerVisibility.FULL),
                        CornerPosition.BOTTOM_RIGHT,new Corner(Resource.INSECT_KINGDOM)
                ),
                0, 34
        )); // 34

        resourceDeck.push(new PlayableCard(
                Resource.INSECT_KINGDOM,
                Map.of(
                        CornerPosition.UP_LEFT,new Corner(CornerVisibility.FULL),
                        CornerPosition.UP_RIGHT,new Corner(SpecialObject.QUILL),
                        CornerPosition.BOTTOM_LEFT,new Corner(Resource.ANIMAL_KINGDOM),
                        CornerPosition.BOTTOM_RIGHT,new Corner(Resource.INSECT_KINGDOM)
                ),
                0, 35
        )); // 35

        resourceDeck.push(new PlayableCard(
                Resource.INSECT_KINGDOM,
                Map.of(
                        CornerPosition.UP_LEFT,new Corner(SpecialObject.MANUSCRIPT),
                        CornerPosition.UP_RIGHT,new Corner(Resource.INSECT_KINGDOM),
                        CornerPosition.BOTTOM_LEFT,new Corner(CornerVisibility.FULL),
                        CornerPosition.BOTTOM_RIGHT,new Corner(Resource.FUNGI_KINGDOM)
                ),
                0, 36
        )); // 36

        resourceDeck.push(new PlayableCard(
                Resource.INSECT_KINGDOM,
                Map.of(
                        CornerPosition.UP_LEFT,new Corner(Resource.INSECT_KINGDOM),
                        CornerPosition.UP_RIGHT,new Corner(Resource.PLANT_KINGDOM),
                        CornerPosition.BOTTOM_LEFT,new Corner(SpecialObject.INKWELL),
                        CornerPosition.BOTTOM_RIGHT,new Corner(CornerVisibility.FULL)
                ),
                0, 37
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
